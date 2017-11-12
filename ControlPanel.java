import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import java.io.File;

/*
Responsible for altering the state of a Canvas object and the dodecahedron it renders given user input
*/
class ControlPanel extends JPanel implements ActionListener {
  Canvas canvas;
  ArrayList<String> polyhedra_filenames;
  JComboBox polyhedra_choices;

  JRadioButton[] rendering_options_buttons;
  String[] rendering_options = { "wireframe", "colors", "shades", "shadows" };

  ButtonGroup btnGroup;
  JRadioButton ccw;
  JRadioButton cw;
  JTextField[] rotation_axis_cooardines;
  JButton rotate;


  JButton render;
  JButton inspect;


  public ControlPanel(Canvas canvas){
    this.canvas = canvas;
    polyhedra_filenames = new ArrayList<String>();
    read_in_filenames();




    //Create the combo box, select item at index 4.
    //Indices start at 0, so 4 specifies the pig.
    polyhedra_choices = new JComboBox(polyhedra_filenames.toArray());
    polyhedra_choices.setSelectedIndex(0);
    polyhedra_choices.addActionListener(this);
    add(polyhedra_choices);

    render = new JButton("Render Polyhedron");
    render.addActionListener(this);
    add(render);


    setPreferredSize(new Dimension(400,500));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    rendering_options_buttons = new JRadioButton[4];
    for(int i =0; i<4; i++){
      rendering_options_buttons[i] = new JRadioButton(rendering_options[i], true);
      rendering_options_buttons[i].addActionListener(this);
      add(rendering_options_buttons[i]);
    }


    add(new JLabel("Rotate"));
    btnGroup = new ButtonGroup();
    ccw = new JRadioButton("Counter Clockwise", true);
    btnGroup.add(ccw);
    add(ccw);
    cw = new JRadioButton("Clockwise");
    btnGroup.add(cw);
    add(cw);

    JPanel coords = new JPanel();
    coords.setLayout(new GridLayout(1,3,30,10));
    rotation_axis_cooardines = new JTextField[3];
    for(int i =0; i<3; i++){
      rotation_axis_cooardines[i] = new JTextField("0");
      coords.add(rotation_axis_cooardines[i]);
    }
    add(coords);
    rotate = new JButton("rotate");
    rotate.addActionListener(this);
    add(rotate);


    inspect = new JButton("Inspect Polyhedron");
    add(inspect);


  }



  public void actionPerformed(ActionEvent evt){
    if(evt.getSource() == render){
      canvas.polyhedron = new Polyhedron("./polyhedra_data/" + polyhedra_choices.getSelectedItem());
      canvas.scene.set_polyhedron(canvas.polyhedron);
      System.out.println(polyhedra_choices.getSelectedItem());

    }

    //determine drawing mode: wireframe our colored faces
    if( rendering_options_buttons[0].isSelected() )
      canvas.wireframe = true;
    else
      canvas.wireframe = false;

    if( rendering_options_buttons[1].isSelected() )
      canvas.colors = true;
    else
      canvas.colors = false;
    if( rendering_options_buttons[2].isSelected() )
      canvas.shades = true;
    else
      canvas.shades = false;

    if( rendering_options_buttons[3].isSelected() )
      canvas.shadows = true;
    else
      canvas.shadows = false;



    //the increment with which we rotate the dodecahedron
    double rotate_increment = Math.toRadians(5);

    //if clockwise, then we rotate by a negative angle
    if( cw.isSelected() )
      rotate_increment = (-1) * rotate_increment;

    //rotate dodecahedron according to input

    if( evt.getSource() == rotate ){

      double[] v = new double[4]; //stores the rotation axis
      /*the ZERO VECTOR (0,0,0) does NOT count as an axis that can be rotated around
      which is why we need to make sure that it is an invalid input
      */
      boolean valid_input_coordinates = false;

      for(int i=0; i<3; i++){
        double input = Double.parseDouble(rotation_axis_cooardines[i].getText());

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

  public void read_in_filenames(){
    File folder = new File("./polyhedra_data");
    File[] list_of_files = folder.listFiles();

    for (int i = 0; i < list_of_files.length; i++) {
      if ( list_of_files[i].isFile() )
        polyhedra_filenames.add( list_of_files[i].getName() );
    }
  }


}
