﻿using System;
using System.Collections.Generic;
using System.Text;
using ATechTools.GUI;
using ATechTools.Util;
using log4net;


namespace ATechTools.GUI.WindowList
{
    public class WindowsListHandler
    {
        ATDataAccessAbstract da;
        Dictionary<string, WindowListI> dictionaryOfWindows = null;
        protected List<WindowListI> listOfWindows = null;
        WindowListClientI windowManager = null;
        private ILog log = LogManager.GetLogger(typeof(WindowsListHandler));



        public WindowListClientI WindowManager
        {
            get { return windowManager; }
            set { windowManager = value; }
        }

        public List<WindowListI> ListOfWindows
        {
            get { return listOfWindows; }
            set { listOfWindows = value; }
        }

        public Dictionary<string, WindowListI> DictionaryOfWindows
        {
            get { return dictionaryOfWindows; }
            set { dictionaryOfWindows = value; }
        }





        public WindowsListHandler(ATDataAccessAbstract da)
        {
            this.da = da;
            this.dictionaryOfWindows = new Dictionary<string, WindowListI>();
            this.listOfWindows = new List<WindowListI>();
        }


        public void AddWLDialog(WindowListI wli)
        {
            wli.WindowsListId = wli.WindowsListBaseId + "_" + DateTime.Now.ToFileTimeUtc();
            this.dictionaryOfWindows.Add(wli.WindowsListId, wli);
            this.listOfWindows.Add(wli);

            if (string.IsNullOrWhiteSpace(wli.WindowsListDescription))
            {
                log.Error("WindowListInterface has no description. Id=" + wli.WindowsListId);
            }

            if ((this.windowManager != null) && (!this.windowManager.IsDisposed))
            {
                this.windowManager.RefreshList();
            }

        }


        public void DisposeWLDialog(WindowListI wli)
        {
            //if (wli == null)
            //    return;

            if (this.dictionaryOfWindows.ContainsKey(wli.WindowsListId))
            {
                WindowListI wlx = this.dictionaryOfWindows[wli.WindowsListId];
                this.listOfWindows.Remove(wlx); //.Remove(wli.WindowsListId);
                this.dictionaryOfWindows.Remove(wli.WindowsListId);
            }

            if ((this.windowManager != null) && (!this.windowManager.IsDisposed))
            {
                this.windowManager.RefreshList();
            }

        }


        public bool ShowManager()
        {
            if ((this.windowManager != null) && (!this.windowManager.IsDisposed))
            {
                this.windowManager.Focus();
                return true;
            }

            return false;
        }

       

    }
}
