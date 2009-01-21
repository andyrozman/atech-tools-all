package com.atech.graphics.dialogs;


import java.awt.Component;

import javax.swing.JDialog;
import javax.swing.JFrame;

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

public abstract class TransferDialog extends JDialog
{

    private static final long serialVersionUID = 6150106762052989231L;

    public TransferDialog(Component parent)
    {
        super((JFrame)parent, "", true);
    }
    
    public Object[] getEmptyInputParameters()
    {
        return new Object[this.getInputParametersCount()];
        
    }
    
    public abstract int getInputParametersCount();
    
    public abstract void setInputParameters(Object[] ip);
    
    
    public abstract void showDialog(boolean visible);
    
    
    public abstract boolean wasAction();
    
    
    public abstract Object[] getResultValues();

    
    public abstract String[] getResultValuesString();

    
}
