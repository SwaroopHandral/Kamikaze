/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

/**
 * 
 * This class handles the LED system on the robot.
 * 
 * @author Swaroop Handral
 * 
 * Makes robot **flashy** woo!
 * 
 */
package frc.robot;

import edu.wpi.cscore.VideoCamera.WhiteBalance;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;
import java.lang.Math;
import java.util.*;

//import edu.wpi.first.wpilibj.programmingsocks;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
import java.util.Dictionary;
public class LED {
    private AddressableLED m_led;
    private AddressableLEDBuffer m_ledBuffer;
    private int onTime;
    private int offTime;
    private boolean toggle;
    private double counter;
    private int index = 0;
    private int stationaryTime = 1;
    private int fadeSpeed;
    private int location;
    private double bfade;
    private Timer timer = new Timer();
    public int PIXELS = 60;
    private double fadeCounter;
    private int fadeLuminescence;
    private int ledindex = 1;
    private int FADE = 0;
    private boolean fadedir;
    private char k = 'c';
    private String LEDarray[] = new String[60];                         // Establishing a new array to set up the other one for more complex projects
    Dictionary<String,String> MCD = new Hashtable<String, String>();

    public void red(int r, int g, int b) {
        r = 255;
        g = 0;
        b = 0;
    }
    public void blue(int r, int g, int b) {
        r = 0;
        g = 0;
        b = 255;
    }
    public void green(int r, int g, int b) {
        r = 0;
        g = 255;
        b = 0;
    }
    public void init() {
        // Must be a PWM header, not MXP or DIO
        m_led = new AddressableLED(7);

        // Reuse buffer
        // Default to a length of 60, start empty output
        // Length is tasking to set, so only set it once, then just update data
        m_ledBuffer = new AddressableLEDBuffer(60);
        m_led.setLength(m_ledBuffer.getLength());

        // Set the data
        m_led.setData(m_ledBuffer);                     // This actually tells the led to do something with the array it's given
        m_led.start();
        this.fadeSpeed = fadeSpeed;
        this.onTime = onTime;
        this.offTime = offTime;
        this.stationaryTime = stationaryTime;                         // How long the light stays in place
        location = 1;
        counter = 0; 
        //fadeCounter = 0;
        blink(10,10);
        //fadeLuminescence(200);

        MCD.put("4", "....-");
        MCD.put("5", ".....");
        MCD.put("6", "-....");
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public void setStripHSV(int h, int s, int v) {
        for (var i = 0; i < m_ledBuffer.getLength(); i++) {
            m_ledBuffer.setHSV(i, h, s, v);
        }
        m_led.setData(m_ledBuffer);
    }
    
    public void setStrip(int r, int g, int b) {
        for (var i = 0;  i < m_ledBuffer.getLength(); i++) {
            if (i % 1 == 0) {                                    // This is to change the interval for lights; one per every x, currently there is no blanks
                m_ledBuffer.setRGB(i, r, g, b); 
                //b=b-4;
                //r=r+3;       
            }                                        // Allows for a clean slide into a different color
        }
        m_led.setData(m_ledBuffer);
    }

    public void stopLED() {                                    // Completely stops output to LEDs
        m_led.stop();                                              
    }
    
    public void blink(int onTime, int offTime) {
        counter = 0;
        toggle = true;
    } 

    public void updateBlink(int r, int g, int b) {
        counter++;
        if (toggle) {  // LEDs ON
            if (counter < onTime) {
                setStrip(r, g, b);
                System.out.println("Blink");
            } 
            else {
                toggle = false;
                counter = 0;
            }
        } 
        else {  // LEDs OFF
            if (counter < offTime) {
                setStrip(0, 0, 0);
            } 
            else {
                toggle = true;
                counter = 0;
            }
        }
    }

    public void solid(int r, int g, int b) {
        setStrip(r, g, b);
    }
    
    public void chasingSeizure(int r, int g, int b, int length, int start, int end, int stationaryTime) {                // fIre bAd
        //System.out.println("Location is:" + location);                  // Debug
        counter++;
        //setLED(location, r, g, b);
        if (counter == stationaryTime) {                            //This needs to be utilized or you will give someone an epileptic seizure

            for(int g1 = start; g1 < m_ledBuffer.getLength()-length; g1++) {
                for(int i = 0; i < length; i++){
                    
                    setLED(g1+i, r, g, b);
                    setLED(g1, 0, 0 , 0);                   //This fixed the problem of the entire strip being orange
                    //m_led.setData(m_ledBuffer);
                }
                m_led.setData(m_ledBuffer);
            }
            /**setLED(location, 0, 0, 0);
            setLED(location+1, r, g, b);
            setLED(location+2, r, g, b);
            setLED(location+3, r, g, b);                        // Add more or delete to adjust length of light
            setLED(location+4, r, g, b);
            setLED(location+5, r, g, b);
            counter = 0;
            location++;**/
            //m_led.setData(m_ledBuffer);
            counter = 0;
        }
    }

    public void chasing(int r, int g, int b, double stationaryTime) {                 // Original of Derek's Frankenstein of my function
        System.out.println("Location is:" + counter);                  // Debug        
        for (var i = 0;  i < m_ledBuffer.getLength(); i++) {
            setLED(i,r,g,b);
            counter = 0;
        }
    }

    public void setLED(int location, int r, int g, int b) {
        location = location % PIXELS;
        m_ledBuffer.setRGB(location, r, g, b);
    }


    public void fade(int hue, double stationaryTime, int speed) { //Hue is color, stationary time is how long each color is present on the leds, speed is how fast the value in question changes 
        counter++;
        if (fadedir) {
            if (counter == stationaryTime) {
                FADE = FADE + speed;
                setStripHSV(hue - FADE, 100, 100);            // Move around FADE to change the effect
                counter = 0;
            }
            if (FADE == 360) {
                FADE = 0;
                fadedir = false;
            }
        }
        else {
            if (counter == stationaryTime) {
                FADE = FADE + speed;
                setStripHSV(hue + FADE, 100, 100);            // Move around FADE to change the effect
                counter = 0;
            }
            if (FADE == 360) {
                FADE = 0;
                fadedir = true;
            }
        }
        m_led.setData(m_ledBuffer);
    }

    public void chasingBackwards(int r, int g, int b, int stationaryTime) {                 // Original of Derek's Frankenstein of my function
        System.out.println("Location is:" + location);                  // Debug
        counter++;
        setLED(location, r, g, b);
        if (counter == stationaryTime/2) {            
            setLED(location, 0, 0, 0);
            setLED(location-1, r, g, b);
            setLED(location-2, r, g, b);
            setLED(location-3, r, g, b);                        // Add more or delete to adjust length of light
            setLED(location-4, r, g, b);
            setLED(location-5, r, g, b);
            setLED(location-5, r, g, b);
            setLED(location-6, r, g, b);
            setLED(location-7, r, g, b);
            setLED(location-8, r, g, b);
            setLED(location-9, r, g, b);
            setLED(location-10, r, g, b);
            counter = 0;
            location--;
            m_led.setData(m_ledBuffer);
        }
    }

    public void morseOnOff(String symbol) { // This will actually set the led to the message
        System.out.print(symbol);
        for (var i = 0; i < m_ledBuffer.getLength(); i++) {
            if(symbol == "-"){
                m_ledBuffer.setHSV(i, 39, 100, 100); 
                Timer.delay(2);     
            }
            
            if(symbol == "."){
                m_ledBuffer.setHSV(i, 39, 100, 100); 
                Timer.delay(1);      
            }

            if(symbol == " "){
                m_ledBuffer.setHSV(i, 39, 100, 100); 
                Timer.delay(1);      
            }
        }
        m_led.setData(m_ledBuffer);
    }

    public void morseCodeSplitter(String message) { // For now, this is going to translate
                                                                                // the message that is inputted and
                                                                                // place it into an array
        for (var i = 0; i < message.length(); i++) {
            char k = message.charAt(i);
            String key = Character.toString(k);
            String morseCodeSegment = MCD.get(key);
            ledTranslator(morseCodeSegment);
        }
        for (var i = 0; i < LEDarray.length; i++) {
            morseOnOff(LEDarray[i]);
        }
        
    }

    public void ledTranslator(String morse) {
        for (var i = 0; i < 5; i++) {
            char k = morse.charAt(i);
            //System.out.println(k);
            String dot = Character.toString(k);
            LEDarray[i] = dot;
        }
    }

    public void underglow(int scale, int stationaryTime) {
        counter++;
        if (fadedir) {
            if (counter == stationaryTime) {
                FADE++;
                setStripHSV(180 - FADE*scale,255,255);            // Move around FADE to change the effect
                counter = 0;
            }
            if (180/scale == FADE) {
                FADE = 0;
                fadedir = false;
            }
        }
        else {
            if (counter == stationaryTime) {
                FADE++;
                setStripHSV(FADE*scale, 255, 255);            // Move around FADE to change the effect
                counter = 0;
            }
            if (180/scale == FADE) {
                FADE = 0;
                fadedir = true;
            }
        }
        m_led.setData(m_ledBuffer);
    }
}
