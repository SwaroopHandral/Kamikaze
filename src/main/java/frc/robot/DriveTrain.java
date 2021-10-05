package frc.robot;


import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveTrain extends DifferentialDrive {
    private static DriveTrain instance;
    private static double MAX_SPEED = 1;
    private static double MAX_TURN = 1;

	private static final Spark
			frontL = new Spark(Constants.DRIVE_FL),
			frontR = new Spark(Constants.DRIVE_FR),
			
			backL = new Spark(Constants.DRIVE_BL),
			backR = new Spark(Constants.DRIVE_BR); 
	private static final SpeedControllerGroup left = new SpeedControllerGroup(frontL, backL);
	private static final SpeedControllerGroup right = new SpeedControllerGroup(frontR, backR); 
    
    public DriveTrain() { 
        super(left,right);

        instance = this;
    }

    public void drive(double speed, double turn) {
        if(speed > MAX_SPEED) {
            speed = MAX_SPEED;
        }
        
        if(speed < -MAX_SPEED) {
            speed = -MAX_SPEED;
        }

        if(turn > MAX_TURN) {
            turn = MAX_TURN;
        }
        
        if(turn < -MAX_TURN) {
            turn = -MAX_TURN;
        }
        arcadeDrive(-speed, turn);
    }
    
}
