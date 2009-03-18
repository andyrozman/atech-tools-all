package com.atech.db.hibernate.tool;

import java.awt.AWTEvent;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeSelectionModel;

import com.atech.utils.ATSwingUtils;

/*
 * This file is part of ATech Tools library.
 * 
 * <one line to give the library's name and a brief idea of what it does.>
 * Copyright (C) 2007 Andy (Aleksander) Rozman (Atech-Software)
 * 
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 * 
 * For additional information about this project please visit our project site
 * on http://atech-tools.sourceforge.net/ or contact us via this emails:
 * andyrozman@users.sourceforge.net or andy@atech-software.com
 * 
 * @author Andy
 */

public class DbTool extends JFrame implements TreeSelectionListener
{

    private static final long serialVersionUID = 3734674824318961963L;
    private JPanel mainPane;
    private JTree tree;

    private DbToolAccess m_da = null;

    // private static boolean playWithLineStyle = false;
    // private static String lineStyle = "Horizontal";

    // private static boolean useSystemLookAndFeel = false;

    private I18nControlDbT ic = null;
    private JPanel panels[] = null;
    // x private int selectedPanel = 0;

    private ATSwingUtils sw_utils = new ATSwingUtils();

    private Hashtable<String, JMenu> menus;
    private Hashtable<String, JMenuItem> menu_items;
    private JMenuBar menu_bar;

    static
    {
        try
        {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        }
        catch (Exception ex)
        {
        }
    }

    public DbTool()
    {

        super();

        enableEvents(AWTEvent.WINDOW_EVENT_MASK);

        m_da = DbToolAccess.createInstance(this);
        m_da.loadApplicationData();
        preInit();
        m_da.m_databases_treeroot.loadData();
        postInit();

    }

    public DbTool(DbToolApplicationInterface intr)
    {

        super();

        m_da = DbToolAccess.createInstance(this);
        // m_da.loadApplicationData();
        preInit();
        m_da.m_databases_treeroot.loadData(intr);
        postInit();

    }

    private void preInit()
    {
        ic = I18nControlDbT.getInstance();
        ATSwingUtils.setI18nControl(ic);

        menu_items = new Hashtable<String, JMenuItem>();
    }

    public void test_for_feature()
    {

    }

    public void postInit()
    {

        createMenus();
        createGUI();

        loadAvailableDatabaseDefinitions();
        createPanels();
        makePanelVisible(0);

        this.setVisible(true);

    }

    public void createGUI()
    {

        this.setResizable(false);

        Toolkit theKit = this.getToolkit();
        Dimension wndSize = theKit.getScreenSize();

        int x, y;
        x = wndSize.width / 2 - 400;
        y = wndSize.height / 2 - 300;

        this.setBounds(x, y, 800, 600);
        this.setTitle(ic.getMessage("DB_TOOL"));

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 0));

        // Create a tree that allows one selection at a time.

        tree = new JTree();
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setModel(new DbToolTreeModel(m_da.m_databases_treeroot));
        tree.setCellRenderer(new DbToolTreeCellRenderer());
        /*
        	tree.setCellRenderer(new TreeCellRenderer()
        	    {
        		/**
        		 * Sets the value of the current tree cell to <code>value</code>.
        		 * If <code>selected</code> is true, the cell will be drawn as if
        		 * selected. If <code>expanded</code> is true the node is currently
        		 * expanded and if <code>leaf</code> is true the node represets a
        		 * leaf and if <code>hasFocus</code> is true the node currently has
        		 * focus. <code>tree</code> is the <code>JTree</code> the receiver is being
        		 * configured for.  Returns the <code>Component</code> that the renderer
        		 * uses to draw the value.
        		 *
        		 * @return	the <code>Component</code> that the renderer uses to draw the value
        		 */
        /*		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus)
        		{
        		    JLabel label = null;
        
        		    if (value instanceof DatabaseSettings)
        		    {
        			DatabaseSettings ds = (DatabaseSettings)value;
        
        			if (ds.isDefault)
        			{
        			    label = new JLabel(ds.toString());
        			    label.setFont(DataAccess.getInstance().getFont(DbToolAccess.FONT_NORMAL_BOLD));
        			}
        			else
        			{
        			    label = new JLabel(ds.toString());
        			    label.setFont(DataAccess.getInstance().getFont(DbToolAccess.FONT_NORMAL));
        			}
        			return label;
        		    }
        		    else
        		    {
        			label = new JLabel(value.toString());
        			label.setFont(DataAccess.getInstance().getFont(DbToolAccess.FONT_NORMAL));
        			return label;
        		    }
        		}
        	    });
        	    */
        tree.addTreeSelectionListener(this);

        JScrollPane treeView = new JScrollPane(tree);

        mainPane = new JPanel();
        mainPane.setLayout(null);

        // Add the scroll panes to a split pane.
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT/*.VERTICAL_SPLIT*/);
        splitPane.setTopComponent(treeView);
        splitPane.setBottomComponent(mainPane);

        Dimension minimumSize = new Dimension(100, 50);
        mainPane.setMinimumSize(minimumSize);
        treeView.setMinimumSize(minimumSize);
        splitPane.setDividerLocation(300); // XXX: ignored in some releases
        // of Swing. bug 4101306
        splitPane.setPreferredSize(new Dimension(400, 300));
        panel.add(splitPane);
        this.add(panel);

    }

    public void createMenus()
    {

        menus = new Hashtable<String, JMenu>();
        menu_bar = new JMenuBar();

        JMenu menu = sw_utils.createMenu("FILE", null, menu_bar);

        menus.put("file", menu);

        menu_items.put("file_quit", sw_utils.createMenuItem(menu, "FILE_QUIT", "FILE_QUIT_DESC", "file_quit", null));

        this.setJMenuBar(menu_bar);

        /*
                // file menu
                this.menu_file = this.createMenu("MN_FILE", null);
                this.createAction(this.menu_file, "MN_QUIT", "MN_QUIT_DESC", "file_quit", null);

                // bgs menu
                this.menu_bgs = this.createMenu("MN_BGS", null);
                this.createAction(this.menu_bgs, "MN_DAILY", "MN_DAILY_DESC", "view_daily", "daily.gif"); 
                this.createAction(this.menu_bgs, "MN_COURSE", "MN_COURSE_DESC", "view_course", "course.gif");
                this.createAction(this.menu_bgs, "MN_SPREAD", "MN_SPREAD_DESC", "view_spread", "spread.gif");
                this.createAction(this.menu_bgs, "MN_FREQUENCY", "MN_FREQUENCY_DESC", "view_freq", "frequency.gif");
                this.menu_bgs.addSeparator();
                this.createAction(this.menu_bgs, "MN_HBA1C", "MN_HBA1C_DESC", "view_hba1c", null);
                this.menu_bgs.addSeparator();
                this.createAction(this.menu_bgs, "MN_FROM_METER", "MN_FROM_METER_DESC", "read_meter", "readmeter.gif");

                //viewDailyAction = new GGCAction("MN_DAILY", "MN_DAILY_DESC", "view_daily");
                //viewDailyAction.putValue(Action.SMALL_ICON, new ImageIcon(getClass().getResource("/icons/daily.gif")));
                //viewCourseGraphAction = new GGCAction("MN_COURSE", "MN_COURSE_DESC", "view_course");
                //viewCourseGraphAction.putValue(Action.SMALL_ICON, new ImageIcon(getClass().getResource("/icons/course.gif")));
                //viewSpreadGraphAction = new GGCAction("MN_SPREAD", "MN_SPREAD_DESC", "view_spread");
                //viewSpreadGraphAction.putValue(Action.SMALL_ICON, new ImageIcon(getClass().getResource("/icons/spread.gif")));
                //viewFrequencyGraphAction = new GGCAction("MN_FREQUENCY", "MN_FREQUENCY_DESC", "view_freq");
                //viewFrequencyGraphAction.putValue(Action.SMALL_ICON, new ImageIcon(getClass().getResource("/icons/frequency.gif")));
                //viewHbA1cAction = new GGCAction("MN_HBA1C", "MN_HBA1C_DESC", "view_hba1c");

                //readMeterAction = new GGCAction("MN_FROM_METER", "MN_FROM_METER_DESC", "read_meter");
                //readMeterAction.putValue(Action.SMALL_ICON, new ImageIcon(getClass().getResource("/icons/readmeter.gif")));




                // food menu
                this.menu_food = this.createMenu("MN_FOOD", null);
                this.createAction(this.menu_food, "MN_NUTRDB_USDB", "MN_NUTRDB_USDB_DESC", "food_nutrition_1", null);
                this.menu_food.addSeparator();
                this.createAction(this.menu_food, "MN_NUTRDB_USER", "MN_NUTRDB_USER_DESC", "food_nutrition_2", null);
                this.menu_food.addSeparator();
                this.createAction(this.menu_food, "MN_MEALS", "MN_MEALS_DESC", "food_meals", null);


                // doctors menu
                this.menu_doctor = this.createMenu("MN_DOCTOR", null);
                this.createAction(this.menu_doctor, "MN_DOCS", "MN_DOCS_DESC", "doc_docs", null);
                this.menu_doctor.addSeparator();
                this.createAction(this.menu_doctor, "MN_APPOINT", "MN_APPOINT_DESC", "doc_appoint", null);
                this.menu_doctor.addSeparator();
                this.createAction(this.menu_doctor, "MN_STOCKS", "MN_STOCKS_DESC", "doc_stocks", null);

                // reports menu
                this.menu_reports = this.createMenu("MN_REPORTS", null);
                this.createAction(this.menu_reports, "MN_PDF_SIMPLE", "MN_PDF_SIMPLE_DESC", "report_pdf_simple", null);
                this.createAction(this.menu_reports, "MN_PDF_EXT", "MN_PDF_EXT_DESC", "report_pdf_extended", null);

                // tools menu
                this.menu_tools = this.createMenu("MN_TOOLS", null);
                this.createAction(this.menu_tools, "MN_PREFERENCES", "MN_PREFERENCES_DESC", "tools_pref", null);
                this.menu_tools.addSeparator();
                this.createAction(this.menu_tools, "MN_DB_MAINT", "MN_DB_MAINT_DESC", "tools_db_maint", null);
                this.menu_tools.addSeparator();
                this.createAction(this.menu_tools, "MN_METER_LIST", "MN_METER_LIST_DESC", "tools_mlist", null);

                //addMenuItem(menu_tools, prefAction);

                // help menu
                this.menu_help = this.createMenu("MN_HELP", null);
                this.menu_help.addSeparator();
                this.createAction(this.menu_help,"MN_CHECK_FOR_UPDATE", "MN_CHECK_FOR_UPDATE_DESC", "hlp_check", null);
                this.menu_help.addSeparator();
                this.createAction(this.menu_help,"MN_ABOUT", "MN_ABOUT_DESC", "hlp_about", null);
        */
    }

    public void createToolbar()
    {

    }

    public void loadAvailableDatabaseDefinitions()
    {
        DatabaseDefinitions dd = new DatabaseDefinitions();

        Hashtable<String, String> table = dd.getTableOfAvailableDatabases();
        ArrayList<String> list = dd.getListOfAvailableDatabases();

        // System.out.println(m_da);

        m_da.createAvailableDatabases(list.size());

        for (int i = 0; i < list.size(); i++)
        {
            String name = (String) list.get(i);

            // System.out.println("name: " + name);

            String id = (String) table.get(name);
            int idn = Integer.parseInt(id);

            DatabaseDefObject ds = new DatabaseDefObject(dd.getDatabaseName(idn), dd.getJdbcDriver(idn), dd.getJdbcURL(idn), dd.getDatabasePort(idn), dd.getHibernateDialect(idn), dd.getHibernateDialectWithout(idn));
            m_da.addAvailableDatabase(i, ds);
            m_da.m_tableOfDatabases.put(ds.name, ds);
        }

    }

    public void createPanels()
    {

        panels = new JPanel[3];

        panels[0] = new PanelRoot(this);
        panels[1] = new PanelDatabaseRoot(this);
        panels[2] = new PanelDatabaseSet(this);
        // panels[1] = new PanelNutritionFoodGroup(this);
        // panels[2] = new PanelNutritionFood(this);
        /*        panels[3] = new ViewDiocesePanel(this);
                panels[4] = new ViewParishPanel(this);
                panels[5] = new ViewDiocesePersonalPanel(this);
                panels[6] = new ViewParishPersonalPanel(this);
                panels[7] = new DiocesePersonalPanel(this);
                panels[8] = new ParishPersonalPanel(this); */

        for (int i = 0; i < panels.length; i++)
        {
            mainPane.add(panels[i]);
        }

        makePanelVisible(0);

    }

    public static final int PANEL_ROOT = 0;
    public static final int PANEL_DATABASE_ROOT = 1;
    public static final int PANEL_DATABASE = 2;

    /**
     *  Windows Process Event
     *  Used for redefining windows command (Close)
     */
    @Override
    protected void processWindowEvent(WindowEvent e)
    {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING)
        {
            System.exit(0);
        }
    }

    /** 
     * Makes selected panel visible
     * @param num 
     */
    public void makePanelVisible(int num)
    {
        // x selectedPanel = num;

        for (int i = 0; i < panels.length; i++)
            if (i == num)
                panels[i].setVisible(true);
            else
                panels[i].setVisible(false);
    }

    /** Required by TreeSelectionListener interface. */
    public void valueChanged(TreeSelectionEvent e)
    {
        System.out.println("DbTool::valueChanged:: " + tree.getLastSelectedPathComponent());

        if (tree.getLastSelectedPathComponent() instanceof DbToolTreeRoot)
        {
            DbToolTreeRoot r = (DbToolTreeRoot) tree.getLastSelectedPathComponent();
            if (r.type == 1)
            {
                PanelDatabaseRoot pdr = (PanelDatabaseRoot) panels[1];
                pdr.setData(r.m_app); // (DbToolTreeRoot)tree.getLastSelectedPathComponent());
                makePanelVisible(DbTool.PANEL_DATABASE_ROOT);
            }
            else
                makePanelVisible(DbTool.PANEL_ROOT);
        }
        else if (tree.getLastSelectedPathComponent() instanceof DbToolApplicationInterface)
        {
            PanelDatabaseRoot pdr = (PanelDatabaseRoot) panels[1];
            pdr.setData((DbToolApplicationInterface) tree.getLastSelectedPathComponent());
            makePanelVisible(DbTool.PANEL_DATABASE_ROOT);
        }
        else if (tree.getLastSelectedPathComponent() instanceof DatabaseSettings)
        {
            PanelDatabaseSet pd = (PanelDatabaseSet) panels[2];
            pd.setData((DatabaseSettings) tree.getLastSelectedPathComponent());
            makePanelVisible(DbTool.PANEL_DATABASE);
        }

    }

    public static void main(String args[])
    {
        /*DbTool tool =*/new DbTool();
    }

}
