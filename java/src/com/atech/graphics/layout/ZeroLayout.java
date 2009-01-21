package com.atech.graphics.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.Hashtable;

import javax.accessibility.AccessibleContext;

/**
 * ZeroLayout is very similar to null layout (no layout). We can, no... we must
 * setBounds before we add Component to Container which uses this layout. On
 * this settings all calculation of object are based. Each Component added to
 * ZeroLayout, must also have type (constraint) set.
 * <p>
 * Valid constraints are: ZeroLayout.STATIC is used if we don't want to resize
 * Component, ZeroLayout.DYNAMIC is used for Componets that will resize just the
 * size (x,y are static, width and height change). ZeroLayout.DYNAMIC_FULL
 * changes all variables (x,y,width and height).
 * <p>
 * ZeroLayout must be constructed with Dimension or width and height parameters.
 * <p>
 * The components are laid out according to their setBounds() sizes.
 * <p>
 * The code for this applet is as follows:
 * <p>
 * <hr>
 * <blockquote>
 * 
 * <pre>
 * import java.awt.*;
 * import java.applet.Applet;
 * import com.atech.graphics.ZeroLayout;
 * 
 * public class buttonDir extends Applet
 * {
 *     public void init() {
 *     setBounds(10,10,200,180);
 *     setLayout(new ZeroLayout(getSize()));
 *     Button bt1 = new Button(&quot;First&quot;);
 *     bt1.setBounds(20,20,30,20);
 *     add.(bt1, ZeroLayout.STATIC);
 *     Button bt2 = new Button(&quot;Second&quot;);
 *     bt2.setBounds(80,80,30,20);
 *     add.(bt2, ZeroLayout.DYNAMIC);
 *     Button bt3 = new Button(&quot;Third&quot;);
 *     bt3.setBounds(120,120,30,20);
 *     add.(bt3, ZeroLayout.DYNAMIC_FULL);
 *   }
 * }
 * </pre>
 * 
 * </blockquote>
 * <hr>
 * <p>
 * 
 * @version 1.00
 * @author Andy {andy@kksonline.com}
 * @see java.awt.Container#add(String, Component)
 * @see java.awt.ComponentOrientation
 */

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

public class ZeroLayout implements LayoutManager, Serializable
{

    private static final long serialVersionUID = 7871795672170516459L;

    /** 
     *  Width of component using this layout
     */
    int width = 0;

    /** 
     *  Height of component using this layout
     */
    int height = 0;

    /**
     *  Elements in ZeroLayout are STATIC if they don't change size or position
     */
    public static String STATIC = "static";

    /**
     *  Elements in ZeroLayout are DYNAMIC if they change size 
     *  (height and width).
     */
    public static String DYNAMIC = "dynamic";

    /**
     *  Elements in ZeroLayout are DYNAMIC_FULL if they change size 
     *  (height and width) and position (x,y).
     */
    public static String DYNAMIC_FULL = "dynamic_full";

    /**
     *  Hashtable containing additional data about Components.
     *  Components are identified with getAccessibleContext.
     */
    Hashtable<AccessibleContext, ZeroElement> components = new Hashtable<AccessibleContext, ZeroElement>();

    /**
     *  Constructs a zero layout with the specified size (used for calculating
     *  "size" of elements).
     *
     * @param   width   width of component using this layout
     * @param   height  height of component using this layout
     */
    public ZeroLayout(int width, int height)
    {
        this.width = width;
        this.height = height;
    }

    /**
     *  Constructs a zero layout with the specified size (used for calculating
     *  "size" of elements).
     *
     * @param   dim  dimnesion of component using this layout
     */
    public ZeroLayout(Dimension dim)
    {
        this((int) dim.getWidth(), (int) dim.getHeight());
    }

    /**
     * Returns size of Layout's parent
     * @param parent 
     * @return 
     */
    public Dimension getSize(Component parent)
    {
        return parent.getSize();
    }

    /**
     * Returns bound of Layout's parent
     * 
     * @param parent 
     * @return 
     */
    public Rectangle getBounds(Component parent)
    {
        return parent.getBounds();
    }

    /**
     * Returns size of Layout's parent... Size that ZeroLayout was 
     * initialized with.
     */
    private Dimension getSize()
    {
        return new Dimension(width, height);
    }

    /**
     * Adds the specified component to the layout, using the specified
     * constraint object.  For zero layout, the constraint must be
     * one of the following constants:  <code>STATIC</code>,
     * <code>DYNAMIC</code> or <code>DYNAMIC_FULL</code>.
     * <p>
     * Most applications do not call this method directly. This method
     * is called when a component is added to a container using the
     * <code>Container.add</code> method with the same argument types.
     * @param   comp         the component to be added.
     * @see     java.awt.Container#add(java.awt.Component, java.lang.Object)
     * @exception   IllegalArgumentException  if the constraint object is not
     *                 a string, or if it not one of the five specified
     *              constants.
     */
    public void addLayoutComponent(String type, Component comp)
    {

        synchronized (comp.getTreeLock())
        {
            if ((type.equalsIgnoreCase("static")) || (type.equalsIgnoreCase("dynamic")) || (type.equalsIgnoreCase("dynamic_full")))
            {
                components.put(comp.getAccessibleContext(), new ZeroElement(comp.getBounds(), type, getSize()));
            }
            else
            {
                throw new IllegalArgumentException("cannot add to layout: you must specify type of component");
            }
        }
        comp.repaint();
    }

    /**
     * Removes the specified component from this zero layout. This
     * method is called when a container calls its <code>remove</code> or
     * <code>removeAll</code> methods. Most applications do not call this
     * method directly.
     * @param   comp   the component to be removed.
     * @see     java.awt.Container#remove(java.awt.Component)
     * @see     java.awt.Container#removeAll()
     */
    public void removeLayoutComponent(Component comp)
    {
        if (components.containsKey(comp.getAccessibleContext()))
            components.remove(comp.getAccessibleContext());

    }

    /**
     * Add Layout Component
     * 
     * @param comp
     * @param hj
     */
    public void addLayoutComponent(Component comp, Object hj)
    {
        addLayoutComponent((String) hj, comp);
    }

    /**
     * Determines the minimum size of the <code>target</code> container
     * using this layout manager.
     * <p>
     * This method is called when a container calls its
     * <code>getMinimumSize</code> method. Most applications do not call
     * this method directly.
     * @return  the minimum dimensions needed to lay out the subcomponents
     *          of the specified container.
     * @see     java.awt.Container
     * @see     java.awt.BorderLayout#preferredLayoutSize
     * @see     java.awt.Container#getMinimumSize()
     */
    public Dimension minimumLayoutSize(Container parent)
    {
        return new Dimension(parent.getWidth(), parent.getHeight());
    }

    /**
     * Determines the preferred size of the <code>target</code>
     * container using this layout manager, based on the components
     * in the container.
     * <p>
     * Most applications do not call this method directly. This method
     * is called when a container calls its <code>getPreferredSize</code>
     * method.
     * @return  the preferred dimensions to lay out the subcomponents
     *          of the specified container.
     * @see     java.awt.Container
     * @see     java.awt.BorderLayout#minimumLayoutSize
     * @see     java.awt.Container#getPreferredSize()
     */
    public Dimension preferredLayoutSize(Container parent)
    {
        return parent.getSize();
    }

    /**
     * Returns the maximum dimensions for this layout given the components
     * in the specified target container.
     * @param parent 
     * @return 
     * @see Container
     * @see #minimumLayoutSize
     * @see #preferredLayoutSize
     */
    public Dimension maximumLayoutSize(Container parent)
    {
        return parent.getSize();
    }

    /**
     * Invalidates the layout, indicating that if the layout manager
     * has cached information it should be discarded.
     * 
     * @param target 
     */
    public void invalidateLayout(Container target)
    {
    }

    /**
     * Lays out the container argument using this zero layout.
     * <p>
     * This method actually reshapes the components in the specified
     * container in order to satisfy the constraints of this
     * <code>ZeroLayout</code> object. We browse through all components
     * and change size of all components.
     * <p>
     * Most applications do not call this method directly. This method
     * is called when a container calls its <code>doLayout</code> method.
     * @see     java.awt.Container
     * @see     java.awt.Container#doLayout()
     */
    public void layoutContainer(Container parent)
    {

        int compsAll = parent.getComponentCount();
        int counter = 0;
        Component com;

        while (counter < compsAll)
        {
            com = parent.getComponent(counter);

            if (components.containsKey(com.getAccessibleContext()))
                com.setBounds(recalculateComponent(parent, (ZeroElement) components.get(com.getAccessibleContext())));
            counter++;
        }
    }

    /**
     *  This methods gets original size of object and recalculates to 
     *  current size of underlaying object.
     */
    private Rectangle recalculateComponent(Component parent, ZeroElement zer)
    {
        Rectangle rec = new Rectangle();

        if (zer.type.startsWith("dynamic"))
        {
            double procX = parent.getSize().getWidth() / 100.0d;
            double procY = parent.getSize().getHeight() / 100.0d;

            if (zer.type.equals("dynamic"))
            {
                rec.x = zer.posX;
                rec.y = zer.posY;
            }
            else
            {
                rec.x = (int) (procX * zer.posXp);
                rec.y = (int) (procY * zer.posYp);
            }
            rec.height = (int) (procY * zer.posHp);
            rec.width = (int) (procX * zer.posWp);
        }
        else
        {
            rec.x = zer.posX;
            rec.y = zer.posY;
            rec.height = zer.posH;
            rec.width = zer.posW;
        }

        return rec;
    }

    /**
     * Returns a string representation of the state of this zero layout.
     * @return    a string representation of this zero layout.
     */
    public String toString()
    {
        return getClass().getName() + " [ZeroLayout]";
    }

}

class ZeroElement
{

    /*
     *  Static values, as definied on initialization.
     */
    int posX = 0;
    int posY = 0;
    int posH = 0;
    int posW = 0;

    /*
     *  Static values (procentual), as definied on initialization.
     */
    double posXp = 0.0d;
    double posYp = 0.0d;
    double posHp = 0.0d;
    double posWp = 0.0d;

    String type = "static";

    /*
     * ZeroElement is used by ZeroLayout, to preserve original data of
     * object and to set it's type and multiplier data.
     */
    ZeroElement(Rectangle rec, String type, Dimension size)
    {
        this.posX = rec.x;
        this.posY = rec.y;
        this.posH = rec.height;
        this.posW = rec.width;
        this.type = type;

        if (type.startsWith("dynamic"))
        {
            double procX = size.getWidth() / 100.0d;
            double procY = size.getHeight() / 100.0d;

            posXp = posX / procX;
            posYp = posY / procY;
            posHp = posH / procY;
            posWp = posW / procX;
        }
    }

    public String toString()
    {
        if (type.equals("static"))
            return "ZeroElement(Static)=[" + posX + "," + posY + "," + posW + "," + posH + "]";
        else if (type.equals("dynamic"))
        {
            return "ZeroElement(Dynamic)=[" + posX + "%, " + posY + "%, " + posWp + "%, " + posHp + "%]";
        }
        else
            return "ZeroElement(Dynamic_Full)=[" + posXp + "%, " + posYp + "%, " + posWp + "%, " + posHp + "%]";
    }
}
