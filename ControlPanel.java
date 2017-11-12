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
  Renderer canvas;
  Scene scene;

  //rendering control
  JRadioButton[] rendering_options_buttons;
  String[] rendering_options = { "Wireframe", "Colors", "Shades", "Shadows" };

  //scene modifications
  ArrayList<String> polyhedra_filenames;
  JComboBox polyhedra_choices;

  JTextField[] camera_coordinates;
  JTextField[] light_source_coordinates;

  JButton render;


  ButtonGroup btnGroup;
  JRadioButton ccw;
  JRadioButton cw;
  JTextField[] rotation_axis_cooardines;
  JButton rotate;

  //the increment with which we rotate the dodecahedron
  final double rotate_increment = Math.toRadians(10);

  JButton bigger;
  JButton smaller;



  public ControlPanel(Renderer canvas, Scene scene){
    this.canvas = canvas;
    this.scene = scene;

    setPreferredSize(new Dimension(400,500));
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


    polyhedra_filenames = new ArrayList<String>();
    read_in_filenames();

    polyhedra_choices = new JComboBox(polyhedra_filenames.toArray());
    polyhedra_choices.setSelectedIndex(0);
    add(polyhedra_choices);

    add(new JLabel("Show..."));
    rendering_options_buttons = new JRadioButton[4];
    for(int i =0; i<4; i++){
      rendering_options_buttons[i] = new JRadioButton(rendering_options[i], false);
      add(rendering_options_buttons[i]);
    }
    rendering_options_buttons[0].setSelected(true);

    add(new JLabel("Set Camera Position"));
    JPanel camera_coords_panel = new JPanel();
    camera_coords_panel.setLayout(new BoxLayout(camera_coords_panel, BoxLayout.X_AXIS));
    camera_coordinates = new JTextField[3];
    camera_coordinates[0] = new JTextField("0.0");
    camera_coordinates[1] = new JTextField("0.0");
    camera_coordinates[2] = new JTextField("10.0");
    for(int i =0; i<3; i++)
      camera_coords_panel.add(camera_coordinates[i]);
    add(camera_coords_panel);

    add(new JLabel("Set Light Source Position"));
    JPanel light_coords_panel = new JPanel();
    light_coords_panel.setLayout(new BoxLayout(light_coords_panel, BoxLayout.X_AXIS));
    light_source_coordinates = new JTextField[3];
    light_source_coordinates[0] = new JTextField("0.0");
    light_source_coordinates[1] = new JTextField("0.0");
    light_source_coordinates[2] = new JTextField("1000.0");
    for(int i =0; i<3; i++)
      light_coords_panel.add(light_source_coordinates[i]);
    add(light_coords_panel);

    render = new JButton("Render Polyhedron");
    render.addActionListener(this);
    add(render);

    add(new JLabel("Operations"));

    add(new JLabel("Rotate Polyhedron"));
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

    add(new JLabel("Scale Polyhedron"));
    bigger = new JButton("Bigger");
    bigger.addActionListener(this);
    add(bigger);
    smaller = new JButton("Smaller");
    smaller.addActionListener(this);
    add(smaller);





  }



  public void actionPerformed(ActionEvent evt){



    if(evt.getSource() == render){
      scene.polyhedron = new Polyhedron( "./polyhedra_data/" + polyhedra_choices.getSelectedItem() );

      canvas.wireframe = rendering_options_buttons[0].isSelected();
      canvas.colors = rendering_options_buttons[1].isSelected();
      canvas.shades = rendering_options_buttons[2].isSelected();
      canvas.shadows = rendering_options_buttons[3].isSelected();

      for(int i=0; i<3; i++)
        scene.camera_position[i] = Double.parseDouble( camera_coordinates[i].getText() );


      for(int i=0; i<3; i++)
        scene.point_light_source_position[i] = Double.parseDouble( light_source_coordinates[i].getText() );


      scene.set_visiblity_flags();
      scene.set_projection();

    }








    if( evt.getSource() == rotate ){
      //the increment with which we rotate the dodecahedron
      double rotate_increment_temp = ccw.isSelected() ? rotate_increment : -rotate_increment;

      double[] v = new double[3]; //stores the rotation axis
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

      if(valid_input_coordinates){
        scene.polyhedron.transform( AffineTransform3D.get_rotation_transform_arb(v, rotate_increment_temp ) );
        scene.set_visiblity_flags();
        scene.set_projection();
      }
    }



    canvas.repaint();


  }

  //MAKE STATIC?
  private void read_in_filenames(){
    File folder = new File("./polyhedra_data");
    File[] list_of_files = folder.listFiles();

    for (int i = 0; i < list_of_files.length; i++) {
      if ( list_of_files[i].isFile() )
        polyhedra_filenames.add( list_of_files[i].getName() );
    }
  }


}
