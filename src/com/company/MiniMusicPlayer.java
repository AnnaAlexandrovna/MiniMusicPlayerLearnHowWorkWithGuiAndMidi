package com.company;

import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;

public class MiniMusicPlayer implements ControllerEventListener{
    static JFrame f = new JFrame("my first music video");
    static MyDrawPanel m1;

    public static void main(String[] args) {
        MiniMusicPlayer miniMusicPlayer = new MiniMusicPlayer();
        miniMusicPlayer.go();
    }
    public void setUpGui(){
        m1 = new MyDrawPanel();
        f.setContentPane(m1);
        f.setBounds(30,30,300,300);
        f.setVisible(true);
    }

    public void go() {
        setUpGui();
        try {
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();

            int[] eventsIWant = {127};
            sequencer.addControllerEventListener(m1, eventsIWant);

            Sequence seq = new Sequence(Sequence.PPQ, 4);
            Track track = seq.createTrack();

            for (int i = 5; i<61;i+=4){
                track.add(makeEvent(144,1,i,100,i));
                track.add(makeEvent(176,1,127,0,i));
                track.add(makeEvent(128,1,i,100,i+2));
            }
            sequencer.setSequence(seq);
            sequencer.setTempoInBPM(120);
            sequencer.start();
        }catch (Exception exception) {exception.printStackTrace();}
    }

    public void controlChange(ShortMessage event){
        System.out.println("lya");

    }

    public static MidiEvent makeEvent (int comd, int chan, int one, int two, int tick){
        MidiEvent event = null;
        try {
            ShortMessage a = new ShortMessage();
            a.setMessage(comd, chan,one,two);
            event = new MidiEvent(a, tick);
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return event;
    }

    class MyDrawPanel extends JPanel implements ControllerEventListener{
        boolean msg = false;

        public void controlChange(ShortMessage event) {
            msg = true;
            repaint();
        }

        public void paintComponent (Graphics g){

            if (msg){
                Graphics2D g2 = (Graphics2D)g;
                int r = (int)(Math.random()*250);
                int gr = (int)(Math.random()*250);
                int b = (int)(Math.random()*250);

                g.setColor(new Color(r,gr,b));

                int ht = (int)((Math.random()*120)+10);
                int width = (int)((Math.random()*120)+10);
                int x = (int)((Math.random()*40)+10);
                int y = (int)((Math.random()*40)+10);
                g.fillRect(x,y,ht,width);
                msg = false;
            }
        }


    }
}
