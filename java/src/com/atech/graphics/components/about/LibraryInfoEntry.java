package com.atech.graphics.components.about;

/**
 *  This file is part of ATech Tools library.
 *  
 *  <one line to give the library's name and a brief idea of what it does.>
 *  Copyright (C) 2007  Andy (Aleksander) Rozman (Atech-Software)
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


public class LibraryInfoEntry
{
    public String name;
    public String version_used;
    public String link_to;
    public String licence_type;
    public String description;
    public String copyright = null;
    public String copyright2 = null;


    public LibraryInfoEntry(String name, String ver_used, String link, String licence_type, String desc)
    {
        this(name, ver_used, link, licence_type, desc, null);
    }

    public LibraryInfoEntry(String name, String ver_used, String link, String licence_type, String desc, String copyright)
    {
        this.name = name;
        this.version_used = ver_used;
        this.link_to = link;
        this.licence_type = licence_type;
        this.description = desc;
        this.copyright = copyright;
    }
    

    public void setCopyRightNotice2(String notice)
    {
        this.copyright2 = notice;
    }


    public void processLink()
    {
        if (!this.link_to.startsWith("http://"))
        {
            this.link_to = "http://" + this.link_to;
        }

        this.link_to = this.link_to.trim();

        if (this.link_to.charAt(this.link_to.length()-1) != '/')
        {
            this.link_to = this.link_to + "/";
        }

    }


    public String getHTMLCode()
    {
        processLink();

        StringBuffer sb = new StringBuffer();

        sb.append("<table width=\"100%\" border=\"0\"><tr><td><font color=\"#006600\">" + this.name + ", v" + this.version_used + " (" + this.licence_type + ")</font></td></tr></table>");
        sb.append("<table width=\"100%\" border=\"0\"><tr><td width=\"10%\">&nbsp;</td><td><font color=\"#000099\">" + this.description + "</font></td></tr></table>");
        sb.append("<table width=\"100%\" border=\"0\"><tr><td width=\"10%\">&nbsp;</td><td><font color=\"#990000\"><a href=\"" + this.link_to + "\">" + this.link_to + "</a></font></td></tr></table>");

        if (this.copyright!=null)
            sb.append("<table width=\"100%\" border=\"0\"><tr><td width=\"10%\">&nbsp;</td><td><font color=\"#000099\">" + this.copyright + "</font></td></tr></table>");

        if (this.copyright2!=null)
            sb.append("<table width=\"100%\" border=\"0\"><tr><td width=\"10%\">&nbsp;</td><td><font color=\"#000099\">" + this.copyright2 + "</font></td></tr></table>");

        sb.append("<br>");

        return sb.toString();
    }



}
