import com.github.jonatino.process.Process;
import com.github.jonatino.process.Processes;
import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;

import javax.swing.*;
import java.awt.*;

class Notorious extends JFrame
{
    public static void main(String[] args) {
        new Notorious();
    }

    public Notorious()
    {
        final long UAV_BASE = 0x16B58FB8;
        Process proc = Processes.byName("ModernWarfare.exe");

        if(proc.id() != 0) {
            final String name = "ModernWarfare.exe";

            final Font tahoma = new Font("tahoma", Font.BOLD, 13);
            setTitle("UAV CHEETO - Notorious");
            setSize(800, 600);
            JLabel info = new JLabel("[PROCESO] " + name + " - [PID] " + proc.id());
            info.setFont(tahoma);
            info.setForeground(Color.WHITE);
            getContentPane().setBackground(new Color(0, 89, 255));
            setLayout(new GridLayout(3, 1));


            add(info);

            final long BASE_ADDY = proc.findModule(name).address();
            final long UAV_OFF1 = UAV_BASE + 0x20;
            final long UAV_OFF2 = UAV_BASE + 0x38;
            final long UAV_OFF3 = UAV_BASE + 0x1C;
            final long UAV_OFF4 = UAV_BASE + 0x38;
            final long UAV_OFF5 = UAV_BASE + 0x3C;

            JLabel KEYS = new JLabel("Toggle ON [F1] - Toggle OFF [F2]");
            KEYS.setForeground(Color.WHITE);
            KEYS.setFont(tahoma);
            add(KEYS);
            String STATE = "UAV ACTIVE: ";
            JLabel STATE_LABEL = new JLabel(STATE + "false");
            STATE_LABEL.setFont(tahoma);
            STATE_LABEL.setForeground(Color.WHITE);

            add(STATE_LABEL);

            GlobalKeyboardHook hook = new GlobalKeyboardHook(true);


            hook.addKeyListener(new GlobalKeyAdapter()
            {
                @Override
                public void keyPressed(GlobalKeyEvent event) {
                    super.keyPressed(event);
                    if(event.getVirtualKeyCode() == GlobalKeyEvent.VK_F1)
                    {
                        STATE_LABEL.setText(STATE + true);

                        byte[] off1 = {0x01,0x00,0x00,0x00};
                        byte[] off2 = {0x01,0x00,0x00,0x00};
                        byte[] off3 = {0x4F, (byte) 0xBB,0x55,0x51};
                        byte[] off4 = {(byte) 0xF6,0x6D, (byte) 0xAE,0x79};
                        byte[] off5 = {0x4F, (byte) 0xBB,0x55,0x51};
                        byte[] off6 = {(byte) 0xF6,0x6d, (byte) 0xAE,0x79};

                        for(byte i : off1)
                            proc.writeByte(BASE_ADDY + UAV_BASE, i);
                        for(byte i : off2)
                            proc.writeByte(BASE_ADDY + UAV_OFF1, i);
                        for(byte i : off3)
                            proc.writeByte(BASE_ADDY + UAV_OFF2, i);
                        for(byte i : off4)
                            proc.writeByte(BASE_ADDY + UAV_OFF3, i);
                        for(byte i : off5)
                            proc.writeByte(BASE_ADDY + UAV_OFF4, i);
                        for(byte i : off6)
                            proc.writeByte(BASE_ADDY + UAV_OFF5, i);




                    }
                    if(event.getVirtualKeyCode() == GlobalKeyEvent.VK_F2)
                    {
                        STATE_LABEL.setText(STATE + false);
                        byte[] off1 = {0x00,0x00,0x00,0x00};
                        byte[] off2 = {0x00,0x00,0x00,0x00};
                        byte[] off3 = {0x00, 0x00,0x00,0x00};
                        byte[] off4 = { 0x00,0x00,0x00,0x00};
                        byte[] off5 = {0x00, 0x00,0x00,0x00};
                        byte[] off6 = {0x00,0x00,  0x00,0x00};

                        for(byte i : off1)
                            proc.writeByte(BASE_ADDY + UAV_BASE, i);
                        for(byte i : off2)
                            proc.writeByte(BASE_ADDY + UAV_OFF1, i);
                        for(byte i : off3)
                            proc.writeByte(BASE_ADDY + UAV_OFF2, i);
                        for(byte i : off4)
                            proc.writeByte(BASE_ADDY + UAV_OFF3, i);
                        for(byte i : off5)
                            proc.writeByte(BASE_ADDY + UAV_OFF4, i);
                        for(byte i : off6)
                            proc.writeByte(BASE_ADDY + UAV_OFF5, i);

                    }
                }
            });




            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            pack();
            setResizable(false);
            setVisible(true);

        }else
            JOptionPane.showMessageDialog(this, "Cannot attach to game :(", "Error", JOptionPane.ERROR_MESSAGE);



    }
}
