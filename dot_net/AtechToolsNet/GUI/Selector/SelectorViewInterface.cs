﻿using System;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;

namespace AtechToolsNet.GUI.Selector
{
    public interface SelectorViewInterface
    {

        SelectorPanel SelectorPanel
        {
            get;
        }


        List<SelectorInterface> FullList
        {
            get;
        }


        List<SelectorInterface> FilteredList
        {
            get;
        }


        void FilterListParameters(int column_index, string value);


        void FilterList();

        void LoadFullList();


        int GetColumnWidth(int column_index);


        string GetColumnHeader(int column_index);


        int ColumnCount
        {
            get;
        }


        bool PrepareGrid(DataGridView dgv);

        string HelpUrl
        {
            get;
        }


        /// <summary>
        /// Method will LoadFullList, and then set this to grid
        /// </summary>
        void ResetList();

        /// <summary>
        /// Action that calls correct Dialog for adding object. 
        /// </summary>
        /// <returns>true if adding succesfull, false if not (on true we need to do refresh of displayed items)</returns>
        bool AddAction();

        /// <summary>
        /// Action that calls correct Dialog for editing object. 
        /// </summary>
        /// <returns>true if editing succesfull, false if not (on true we need to do refresh of displayed items)</returns>
        bool EditAction(SelectorInterface si);


        bool HasPrintingReports();

        List<object> PrintingReportDefinitions
        {
            get;
        }


        void Resize();


        string SelectorTitle
        { 
            get; 
        }

    }
}
