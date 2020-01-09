package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

// this makes the code in the autonomous less clunky

public class TFOD
{
    private static final String TFOD_MODEL_ASSET = "Skystone.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Stone";
    private static final String LABEL_SECOND_ELEMENT = "Skystone";

    private static final String VUFORIA_KEY = "AVC8YYn/////AAABmTHcycHSw0jDoQ+NF6xCbB0tUGyWIV0iiSkEbyZIziI90kxeCYRqCG7+1p7yDhFHRxqP7ShVVBH8UxcYqEaPEX2TBixRKadnQhqJeGfqEsBcZLren0DnbUywTotQPe4qsssno9xfq50fMYU3AxTRURF66n5ATUisE21fF7U372VPIBnPgR27/HlkKMKXkzVko3LDiwX4NMXBMxR9Egez2RVAK8WvGAJ+n9E1OVKGVMnAwo555OUh56yMA/6BxfNsMVUts7QMEFMev68Am+CZ12ilxov/mV+xS+TOO/ZbWCxCQZUd+HAuTxcjDw3s5Qsy7aspYQRszP46cLdDyvzNnwL11fkeRn1ZK7jlK2fD3FRZ";

    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;

    public List<Recognition> recognizedObjects;


    public boolean initialize(HardwareMap hardwareMap)
    {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CameraDirection.BACK;

        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        if (ClassFactory.getInstance().canCreateTFObjectDetector())
        {
            int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
            TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
            tfodParameters.minimumConfidence = 0.85;
            tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
            tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);

            if (tfod != null)
            {
                tfod.activate();
            }
        }
        else
        {
            return false;
        }
        return true;
    }

    public void updateDetectedObjects()
    {
        recognizedObjects = tfod.getUpdatedRecognitions();
    }

    public void shutDown()
    {
        tfod.shutdown();
    }

    public void sendInfo(LinearOpMode thing)
    {
        if(recognizedObjects != null && recognizedObjects.size() > 0)
        {
            thing.telemetry.addData("Objects Detected:", recognizedObjects.size());
            thing.telemetry.addData("type:", recognizedObjects.get(0).getLabel());
        }else {thing.telemetry.addData("No Objects", "sorry my guy");}
    }


    public double moveTowardSkystone(Recognition skystone)
    {
        double angle = skystone.estimateAngleToObject(AngleUnit.DEGREES);

        // the further away from the desired angle, the more power it will output.
        // if it is close, it will slowly move towards it.

        // this could be moved to become a public variable or maybe into the other file
        // the phones orientation might have an effect on these numbers -> positive or not...

        double desiredLeftBound = 5.5;
        double desiredRightBound = -2.5;

        if(angle > desiredLeftBound)
        {
            // move right
            // return a number between 0 and 1
            return 1/(.1 * (angle - desiredLeftBound));

        }
        else if(angle < desiredRightBound)
        {
            // move left
            // return  between a -1 and 0
            return 1/(angle - desiredRightBound);

        }
        return 0;
    }


}