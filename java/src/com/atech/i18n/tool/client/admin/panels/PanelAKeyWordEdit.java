package com.atech.i18n.tool.client.admin.panels;

import com.atech.graphics.components.EditableAbstractPanel;
import com.atech.i18n.tool.client.DataAccessTT;
import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.Rectangle;
import javax.swing.JTextField;

public class PanelAKeyWordEdit extends EditableAbstractPanel
{
    
    
    private JLabel jLabel = null;
    private JTextField jTextField = null;

    public PanelAKeyWordEdit()
    {
        super(true, DataAccessTT.getInstance().getI18nControlInstance());
		initialize();
        init();
    }
    

    /**
     * This method initializes this
     * 
     */
    private void initialize() {
        jLabel = new JLabel();
        jLabel.setText("Keyword Edit");
        jLabel.setBounds(new Rectangle(216, 37, 38, 16));
        this.setLayout(null);
        this.setSize(new Dimension(474, 437));
        this.add(jLabel, null);
        this.add(getJTextField(), null);
    		
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


    /**
     * This method initializes jTextField	
     * 	
     * @return javax.swing.JTextField	
     */
    private JTextField getJTextField()
    {
        if (jTextField == null)
        {
            jTextField = new JTextField();
            jTextField.setBounds(new Rectangle(169, 120, 182, 29));
        }
        return jTextField;
    }

}  //  @jve:decl-index=0:visual-constraint="10,10"
