package com.atech.graphics.dialogs.selector;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.table.JTableHeader;

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


public class TableHeaderListener extends MouseAdapter 
{
    JTableHeader   header;
    SortButtonRenderer renderer;
  
    public TableHeaderListener(JTableHeader header,SortButtonRenderer renderer) {
      this.header   = header;
      this.renderer = renderer;
    }
  
    public void mousePressed(MouseEvent e) {
      int col = header.columnAtPoint(e.getPoint());
//x      int sortCol = header.getTable().convertColumnIndexToModel(col);
      renderer.setPressedColumn(col);
      renderer.setSelectedColumn(col);
      header.repaint();
      
      if (header.getTable().isEditing()) {
        header.getTable().getCellEditor().stopCellEditing();
      }
      /* X
      boolean isAscent;
      if (SortButtonRenderer.DOWN == renderer.getState(col)) 
      {
          isAscent = true;
      } 
      else 
      {
        isAscent = false;
      }
      */
//      ((SortableTableModel)header.getTable().getModel())
//        .sortByColumn(sortCol, isAscent);    
    }
  
    public void mouseReleased(MouseEvent e) 
    {
//x        int col = header.columnAtPoint(e.getPoint());
        renderer.setPressedColumn(-1);                // clear
        header.repaint();
    }
  }

