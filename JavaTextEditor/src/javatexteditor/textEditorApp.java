import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

// sudo mount -o remount,size=10G,noatime /tmp

public class textEditorApp extends Frame{
    String fileName;
    final TextArea textArea;
    MenuItem itemOpen;
    MenuItem itemSave;
    MenuItem itemExit;

    public static void main (String[] args){
        new textEditorApp();
    }

    public textEditorApp(){
        super("Aplikasi Text Editor Sederhana");
        setSize(400, 250);

        MenuBar menuBar = new MenuBar();
        setMenuBar(menuBar);
        Menu menuFile = new Menu("File");
        Menu menuWarna = new Menu("Color");
        menuBar.add(menuFile);
        menuBar.add(menuWarna);


        itemOpen = new MenuItem("Open...");
        itemSave = new MenuItem("Save");
        MenuItem itemStrip = new MenuItem("---");
        itemExit = new MenuItem("Exit");

        menuFile.add(itemOpen);
        menuFile.add(itemSave);
        menuFile.add(itemStrip);
        menuFile.add(itemExit);

        Menu subMenuBgColor = new Menu("Background Color");
        Menu subMenuTextColor = new Menu("Text Color");
        menuWarna.add(subMenuBgColor);
        menuWarna.add(subMenuTextColor);

        MenuItem itemWhite = new MenuItem("White");
        MenuItem itemGray = new MenuItem("Gray");
        MenuItem itemBlack = new MenuItem("Black");
        MenuItem itemBlue = new MenuItem("Blue");

        subMenuBgColor.add(itemWhite);
        subMenuBgColor.add(itemGray);
        subMenuTextColor.add(itemBlack);
        subMenuTextColor.add(itemBlue);

        Button btnClose = new Button("Selesai");

        this.textArea = new TextArea("");
        this.textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        add(textArea);
        add("South", btnClose);

        //Event Listener
        try {
            btnClose.addActionListener(new textEditorApp.eventListener());
            this.itemOpen.addActionListener(new textEditorApp.eventListener());
            this.itemSave.addActionListener(new textEditorApp.eventListener());
            this.itemExit.addActionListener(new textEditorApp.eventListener());
            itemWhite.addActionListener(new textEditorApp.eventListener());
            itemGray.addActionListener(new textEditorApp.eventListener());
            itemBlack.addActionListener(new textEditorApp.eventListener());
            itemBlue.addActionListener(new textEditorApp.eventListener());
            addWindowListener(new windowEvent());
        }catch (NullPointerException npex){
            System.err.println("System Error: "+npex.getMessage());
        }
        setVisible(true);
    }

    public void open(){
        FileDialog fileDialog = new FileDialog(this, "Open FIle");
        fileDialog.setVisible(true);

        this.fileName = fileDialog.getFile();
        String directoryName = fileDialog.getDirectory();
        this.fileName = directoryName+this.fileName;

        if (this.fileName == null){
            return;
        }

        FileReader fileInput = null;
        String data = "";

        try {
            fileInput = new FileReader(this.fileName);
            BufferedReader inputStream = new BufferedReader(fileInput);
            while (true){
                String dataLine = inputStream.readLine();
                if (dataLine == null){
                    break;
                }
            }
            fileInput.close();
        }catch (IOException ioex){
            System.err.println("Terjadi Error: "+ioex.getMessage());
        }
    }

    public void save(){
        try {
            this.fileName = JOptionPane.showInputDialog("Masukkan Nama File");
            FileWriter fileOutput = new FileWriter(this.fileName);
            String dataTextArea = this.textArea.getText();
            fileOutput.write(dataTextArea);
            fileOutput.flush();
            fileOutput.close();
        }catch (IOException ioex){
            System.err.println("Terjadi Error: "+ioex.getMessage());
        }
    }

    class eventListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String command = actionEvent.getActionCommand();
            switch (command){
                case "Open..." :
                    open();
                    break;
                case "Save" :
                    save();
                    break;
                case "Exit" :
                    dispose();
                    break;
                case "White" :
                    textArea.setBackground(Color.WHITE);
                    break;
                case "Gray" :
                    textArea.setBackground(Color.GRAY);
                    break;
                case "Black" :
                    textArea.setForeground(Color.BLACK);
                    break;
                case "Blue" :
                    textArea.setForeground(Color.BLUE);
                    break;
                default:
                    System.exit(0);
                    break;
            }
        }
    }

    class windowEvent extends WindowAdapter {
        public void windowClosing (WindowEvent e){
            System.exit(0);
        }
    }
}
