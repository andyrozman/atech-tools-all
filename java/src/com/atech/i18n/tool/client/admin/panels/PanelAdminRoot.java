package com.atech.i18n.tool.client.admin.panels;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JLabel;

import com.atech.graphics.components.EditableAbstractPanel;
import com.atech.i18n.tool.client.DataAccessTT;

public class PanelAdminRoot extends EditableAbstractPanel
{

    private JLabel jLabel = null;

    public PanelAdminRoot()
    {
        super(false, DataAccessTT.getInstance().getI18nControlInstance());
        initialize();
        init();
    }
    

    /**
     * This method initializes this
     * 
     */
    private void initialize() {
        jLabel = new JLabel();
        jLabel.setText("Module View");
        jLabel.setBounds(new Rectangle(216, 37, 38, 16));
        this.setLayout(null);
        this.setSize(new Dimension(474, 437));
        this.add(jLabel, null);
            
    }


    public void init()
    {
        
    }
    
    
    @Override
    public String getWarningString(int action_type)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean hasDataChanged()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean saveData()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setData(Object object)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setParent(Object object)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setParentRoot(Object object)
    {
        // TODO Auto-generated method stub
        
    }
    
    
}
