package norman.example.gui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Properties;
import java.util.ResourceBundle;

public class MainFrame extends JFrame implements ActionListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainFrame.class);
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("norman.example.gui.MainFrame");
    private Properties appProps;
    private JDesktopPane desktop;
    private JMenuItem optionsFileItem;
    private JMenuItem exitFileItem;

    public MainFrame(Properties appProps) throws HeadlessException {
        super();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.appProps = appProps;
        initComponents();
        int width = Integer.parseInt(appProps.getProperty("main.frame.width"));
        int height = Integer.parseInt(appProps.getProperty("main.frame.height"));
        setSize(width, height);
        int x = Integer.parseInt(appProps.getProperty("main.frame.location.x"));
        int y = Integer.parseInt(appProps.getProperty("main.frame.location.y"));
        setLocation(x, y);
    }

    private void initComponents() {
        LOGGER.debug("Initializing window components.");
        setTitle(BUNDLE.getString("title"));
        desktop = new JDesktopPane();
        setContentPane(desktop);
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu(BUNDLE.getString("menu.file"));
        menuBar.add(fileMenu);
        optionsFileItem = new JMenuItem(BUNDLE.getString("menu.file.options"));
        fileMenu.add(optionsFileItem);
        optionsFileItem.addActionListener(this);
        fileMenu.add(new JSeparator());
        exitFileItem = new JMenuItem(BUNDLE.getString("menu.file.exit"));
        fileMenu.add(exitFileItem);
        exitFileItem.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(exitFileItem)) {
            LOGGER.debug("Processing exit menu item.");
            processWindowEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        } else if (actionEvent.getSource().equals(optionsFileItem)) {
            //options();
        }
    }

    @Override
    protected void processWindowEvent(WindowEvent windowEvent) {
        if (windowEvent.getSource() == this && windowEvent.getID() == WindowEvent.WINDOW_CLOSING) {

            // Get current size and position of UI and remember it.
            Dimension size = getSize();
            appProps.setProperty("main.frame.width", Integer.toString(size.width));
            appProps.setProperty("main.frame.height", Integer.toString(size.height));
            Point location = getLocation();
            appProps.setProperty("main.frame.location.x", Integer.toString(location.x));
            appProps.setProperty("main.frame.location.y", Integer.toString(location.y));

            // Save the properties file to a local file.
            try {
                Application.storeProps(appProps);
            } catch (LoggingException e) {
                JOptionPane.showMessageDialog(this, BUNDLE.getString("error.message.saving.window.size.and.location"),
                        BUNDLE.getString("error.dialog.title"), JOptionPane.ERROR_MESSAGE);
            }
        }
        super.processWindowEvent(windowEvent);
    }
}
