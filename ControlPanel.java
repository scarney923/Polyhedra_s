import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;

/*
Responsible for altering the state of a Canvas object and the dodecahedron it renders given user input
*/
class ControlPanel extends JPanel implements ActionListener {
  Canvas canvas;

  ButtonGroup btnGroup1;  //we use a ButtonGroup to make the buttons selection state mutually exclusive
  JRadioButton wireframe;
  JRadioButton colors;

  ButtonGroup btnGroup2;
  JRadioButton ccw;
  JRadioButton cw;

  JButton[] axis_buttons;
  String[] axis_labels = {"x-axis", "y-axis", "z-axis", "arbitrary axis"};
  JSlider[] vector_components;

  public ControlPanel(Canvas canvas){
    this.canvas = canvas;
    setPreferredSize(new Dimension(400,500));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    btnGroup1 = new ButtonGroup();
    wireframe = new JRadioButton("Wireframe", true);
    wireframe.addActionListener(this);
    btnGroup1.add(wireframe);
    add(wireframe);
    colors = new JRadioButton("Colors");
    colors.addActionListener(this);
    btnGroup1.add(colors);
    add(colors);

    btnGroup2 = new ButtonGroup();
    ccw = new JRadioButton("Counter Clockwise", true);
    btnGroup2.add(ccw);
    add(ccw);
    cw = new JRadioButton("Clockwise");
    btnGroup2.add(cw);
    add(cw);

    //create and add buttons for rotating around Cartesian coordinate axes
    axis_buttons = new JButton[4];
    JPanel coord_axis = new JPanel();
    coord_axis.setLayout(new BoxLayout(coord_axis, BoxLayout.X_AXIS));
    for(int i = 0; i < 3; i++){
      axis_buttons[i] = new JButton(axis_labels[i]);
      axis_buttons[i].addActionListener(this);
      coord_axis.add(axis_buttons[i]);
    }
    add(coord_axis);

    //create and add button and accompanying sliders for rotating around arbitrary axis
    JPanel arb_axis = new JPanel();
    arb_axis.setLayout(new BoxLayout(arb_axis, BoxLayout.X_AXIS));
    axis_buttons[3] = new JButton(axis_labels[3]);
    axis_buttons[3].addActionListener(this);
    arb_axis.add(axis_buttons[3]);
    vector_components = new JSlider[3];
    for(int i = 0; i<3; i++){
			vector_components[i] = new JSlider(JSlider.VERTICAL,-10,10,0);
      vector_components[i].setMajorTickSpacing(1);
      vector_components[i].setPaintTicks(true);
      vector_components[i].setPaintLabels(true);
			arb_axis.add( vector_components[i] );
		}
    add(arb_axis);



  }



  public void actionPerformed(ActionEvent evt){
    //determine drawing mode: wireframe our colored faces
    if( wireframe.isSelected() )
      canvas.wireframe = true;
    else
      canvas.wireframe = false;

    if( colors.isSelected() )
      canvas.colors = true;
    else
      canvas.colors = false;


    //the increment with which we rotate the dodecahedron
    double rotate_increment = Math.toRadians(5);

    //if clockwise, then we rotate by a negative angle
    if( cw.isSelected() )
      rotate_increment = (-1) * rotate_increment;

    //rotate dodecahedron according to input
    if(evt.getSource() == axis_buttons[0]){
      canvas.polyhedron.transform( AffineTransform3D.get_rotation_transform_xaxis( rotate_increment ) );
    }
    if(evt.getSource() == axis_buttons[1]){
      canvas.polyhedron.transform( AffineTransform3D.get_rotation_transform_yaxis( rotate_increment ) );
    }
    if(evt.getSource() == axis_buttons[2]){
      canvas.polyhedron.transform( AffineTransform3D.get_rotation_transform_zaxis( rotate_increment ) );
    }
    if(evt.getSource() == axis_buttons[3]){

      double[] v = new double[4]; //stores the rotation axis
      /*the ZERO VECTOR (0,0,0) does NOT count as an axis that can be rotated around
      which is why we need to make sure that it is an invalid input
      */
      boolean valid_input_coordinates = false;

      for(int i=0; i<3; i++){
        double input = (double) vector_components[i].getValue(); //get input from slider

        if( input != 0 ) //if value is not zero, then we set the valid input flag to 'true'
          valid_input_coordinates = true;

        v[i] = input;
      }
      v[3] = 1.0; //set the (homogeneous) w component to 1.0

      if(valid_input_coordinates) //if all three input coordinates were 0, then the flag was never set to true
        canvas.polyhedron.transform( AffineTransform3D.get_rotation_transform_arb(v, rotate_increment ) );
    }

    canvas.repaint();


  }


}
