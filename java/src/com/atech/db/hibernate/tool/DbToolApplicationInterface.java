package com.atech.db.hibernate.tool;

import java.util.Hashtable;

/**
 *  This file is part of ATech Tools library.
 *  
 *  DbToolApplicationInterface - this is interface for all applications supporting DbTool
 *  Copyright (C) 2006  Andy (Aleksander) Rozman (Atech-Software)
 *  
 *  
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA 
 *  
 *  
 *  For additional information about this project please visit our project site on 
 *  http://atech-tools.sourceforge.net/ or contact us via this emails: 
 *  andyrozman@users.sourceforge.net or andy@atech-software.com
 *  
 *  @author Andy
 *
*/

public interface DbToolApplicationInterface
{

    /**
     * Get Application Name
     * 
     * @return
     */
    String getApplicationName();

    /**
     * Get Application Database Config
     * 
     * @return
     */
    String getApplicationDatabaseConfig();

    /**
     * Init Static Dbs
     */
    void initStaticDbs();

    /**
     * Load Config
     */
    void loadConfig();

    /**
     * Save Config
     */
    void saveConfig();

    /**
     * Get First Available Database
     * 
     * @return get number of first database (this can be either 0 or 1)
     */
    int getFirstAvailableDatabase();

    /**
     * Get Static Databases
     * 
     * @return
     */
    Hashtable<String, DatabaseSettings> getStaticDatabases();

    /**
     * Get Custom Databases
     * 
     * @return
     */
    Hashtable<String, DatabaseSettings> getCustomDatabases();

    /**
     * Get All Databases
     * 
     * @return
     */
    Hashtable<String, DatabaseSettings> getAllDatabases();

    /**
     * Get Database
     * 
     * @param index
     * @return
     */
    DatabaseSettings getDatabase(int index);

    /**
     * Get Selected Database
     * 
     * @return
     */
    DatabaseSettings getSelectedDatabase();

}
