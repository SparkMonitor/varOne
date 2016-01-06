package com.varone.exec;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Label;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.PopupMenu;
import java.awt.SystemColor;
import java.awt.SystemTray;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ClientConsole {
    
   // private static String RESOURCES_PATH = "/com/haredb/hbaseclient/exec/resource";
	private static String RESOURCES_PATH = "";
    private Font font;
    private SystemTray tray;
    private TrayIcon trayIcon;
    private Frame frame;
    private TextField urlText;
    private Button startBrowser;

    private boolean isWindows;
    
    private AwtListener awtListener = new AwtListener();

    public ClientConsole() {
        isWindows = System.getProperty("os.name", "").startsWith("Windows");
    }
    
    public void launch(String url) throws Exception {
        this.openBrowser( url );
        this.loadFont();
        if (!this.createTrayIcon()) {
            this.showWindow();
        }
    }
    
    public void openBrowser(String url) throws Exception {
        try {
            String osName = System.getProperty("os.name", "linux").toLowerCase();
            Runtime rt = Runtime.getRuntime();

            if (osName.indexOf("windows") >= 0) {
                rt.exec(new String[] { "rundll32", "url.dll,FileProtocolHandler", url });
            } else if (osName.indexOf("mac") >= 0 || osName.indexOf("darwin") >= 0) {
                // Mac OS: to open a page with Safari, use "open -a Safari"
                Runtime.getRuntime().exec(new String[] { "open", url });
            } else {
                String[] browsers = { "google-chrome", "firefox", "mozilla-firefox",
                        "mozilla", "konqueror", "netscape", "opera", "midori" };
                boolean ok = false;
                for (String b : browsers) {
                    try {
                        rt.exec(new String[] { b, url });
                        ok = true;
                        break;
                    } catch (Exception e) {
                        // ignore and try the next
                    }
                }
                if (!ok) {
                    // No success in detection.
                    throw new Exception("Browser detection failed.");
                }
            }
        } catch (Exception e) {
            throw new Exception("Failed to start a browser to open the URL " + url + ": " + e.getMessage());
        }
    }    

    public void loadFont() {
        if (isWindows) {
            font = new Font("Dialog", Font.PLAIN, 11);
        } else {
            font = new Font("Dialog", Font.PLAIN, 12);
        }
    }

    public boolean createTrayIcon() {
        try {
            boolean supported = SystemTray.isSupported();
            if (!supported) {
                System.out.println("SystemTray not supported!");
                return false;
            }
            
            PopupMenu menuConsole = this.buildTrayIconMenu();

            tray = SystemTray.getSystemTray();

            Dimension dim = tray.getTrayIconSize();
            String iconFile;
            
            if (dim.width >= 24 && dim.height >= 24) {
                iconFile = RESOURCES_PATH + "/h2-24.png";
            } else if (dim.width >= 22 && dim.height >= 22) {
                // for Mac OS X 10.8.1 with retina display:
                // the reported resolution is 22 x 22, but the image
                // is scaled and the real resolution is 44 x 44
                iconFile = RESOURCES_PATH + "/h2-64-t.png";
                // iconFile = "/org/h2/res/h2-22-t.png";
            } else {
                iconFile = RESOURCES_PATH + "/h2.png";
            }
            Image icon = loadImage(iconFile);

            trayIcon = new TrayIcon(icon, "HareDB Client", menuConsole);
            trayIcon.addMouseListener(awtListener);
            tray.add(trayIcon);

            return true;
        } catch (Exception ex) {
            return false;
        }

    }
    
    private PopupMenu buildTrayIconMenu(){
        
        PopupMenu result = new PopupMenu();
        
//        MenuItem itemConsole = new MenuItem("HareDB Console");
//        itemConsole.setActionCommand("console");
//        itemConsole.addActionListener(awtListener);
//        itemConsole.setFont(font);
//        result.add(itemConsole);
//        
//        MenuItem itemStatus = new MenuItem("Status");
//        itemStatus.setActionCommand("status");
//        itemStatus.addActionListener(awtListener);
//        itemStatus.setFont(font);
//        result.add(itemStatus);
        
        MenuItem itemExit = new MenuItem("Exit VarOne Client");
        itemExit.setFont(font);
        itemExit.setActionCommand("exit");
        itemExit.addActionListener(awtListener);
        result.add(itemExit);
        
        return result;
    }
    
    private void showWindow() {
        if (frame != null) {
            return;
        }
        if (!GraphicsEnvironment.isHeadless()){
            System.out.println("GraphicsEnvironment.isHeadless is true. Cannot show window.");
            return;
        }
        frame = new Frame("H2 Console");
        frame.addWindowListener(this.awtListener);
        Image image = loadImage("/org/h2/res/h2.png");
        if (image != null) {
            frame.setIconImage(image);
        }
        frame.setResizable(false);
        frame.setBackground(SystemColor.control);

        GridBagLayout layout = new GridBagLayout();
        frame.setLayout(layout);

        // the main panel keeps everything together
        Panel mainPanel = new Panel(layout);

        GridBagConstraints constraintsPanel = new GridBagConstraints();
        constraintsPanel.gridx = 0;
        constraintsPanel.weightx = 1.0D;
        constraintsPanel.weighty = 1.0D;
        constraintsPanel.fill = GridBagConstraints.BOTH;
        constraintsPanel.insets = new Insets(0, 10, 0, 10);
        constraintsPanel.gridy = 0;

        GridBagConstraints constraintsButton = new GridBagConstraints();
        constraintsButton.gridx = 0;
        constraintsButton.gridwidth = 2;
        constraintsButton.insets = new Insets(10, 0, 0, 0);
        constraintsButton.gridy = 1;
        constraintsButton.anchor = GridBagConstraints.EAST;

        GridBagConstraints constraintsTextField = new GridBagConstraints();
        constraintsTextField.fill = GridBagConstraints.HORIZONTAL;
        constraintsTextField.gridy = 0;
        constraintsTextField.weightx = 1.0;
        constraintsTextField.insets = new Insets(0, 5, 0, 0);
        constraintsTextField.gridx = 1;

        GridBagConstraints constraintsLabel = new GridBagConstraints();
        constraintsLabel.gridx = 0;
        constraintsLabel.gridy = 0;

        Label label = new Label("H2 Console URL:", Label.LEFT);
        label.setFont(font);
        mainPanel.add(label, constraintsLabel);

        urlText = new TextField();
        urlText.setEditable(false);
        urlText.setFont(font);
        urlText.setText( RESOURCES_PATH );
        if (isWindows) {
            urlText.setFocusable(false);
        }
        mainPanel.add(urlText, constraintsTextField);

        startBrowser = new Button("Start Browser");
        startBrowser.setFocusable(false);
        startBrowser.setActionCommand("console");
        startBrowser.addActionListener(this.awtListener);
        startBrowser.setFont(font);
        mainPanel.add(startBrowser, constraintsButton);
        frame.add(mainPanel, constraintsPanel);

        int width = 300, height = 120;
        frame.setSize(width, height);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((screenSize.width - width) / 2, (screenSize.height - height) / 2);
        try {
            frame.setVisible(true);
        } catch (Throwable t) {
            // ignore
            // some systems don't support this method, for example IKVM
            // however it still works
        }
        try {
            // ensure this window is in front of the browser
            frame.setAlwaysOnTop(true);
            frame.setAlwaysOnTop(false);
        } catch (Throwable t) {
            // ignore
        }
    }

    private static Image loadImage(String name) {
        try {
            byte[] imageData = resourceAsByteArray(name);
            if (imageData == null) {
                return null;
            }
            return Toolkit.getDefaultToolkit().createImage(imageData);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void shutdown() {
        tray.remove(trayIcon);
        System.exit(0);
    }
    
        

    private static byte[] resourceAsByteArray(String name) throws IOException {
        InputStream in = ClientConsole.class.getResourceAsStream(name);
        if (in == null)
            return null;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[16384];

        while ((nRead = in.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        buffer.flush();
        return buffer.toByteArray();
    }

    class AwtListener implements ActionListener, MouseListener, WindowListener {

        @Override
        public void windowOpened(WindowEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void windowClosing(WindowEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void windowClosed(WindowEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void windowIconified(WindowEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void windowDeiconified(WindowEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void windowActivated(WindowEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void windowDeactivated(WindowEvent e) {
            // TODO Auto-generated method stub

        }

        // --------------------------------------------------------

        @Override
        public void mouseClicked(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mousePressed(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseExited(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        // --------------------------------------------------------

        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if ("exit".equals(command)) {
                shutdown();
            }
        }

    }

}