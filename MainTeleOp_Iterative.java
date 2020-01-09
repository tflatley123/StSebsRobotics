/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


@TeleOp(name="MainTeleOp Iterative", group="Opmode")
//@Disabled
public class MainTeleOp_Iterative extends OpMode
{
    MainRobotHardware robot = new MainRobotHardware();
    ElapsedTime runtime = new ElapsedTime();


    private double powerMultiplier = 1;

    // runs once (on init)
    @Override
    public void init()
    {
        telemetry.addData("Status", "Initializing");

        robot.init(hardwareMap);

        telemetry.addData("Status", "Initialized");
    }


    // runs repeatedly (after Init, before Play)
    @Override
    public void init_loop()
    {
        // prolly not using this
    }


    // runs once (after play)
    @Override
    public void start()
    {
        runtime.reset();
    }


     // runs repeatedly (after play, before stop)
    @Override
    public void loop()
    {
        // tank drive
        robot.backleftPower = gamepad1.left_stick_y;
        robot.frontleftPower = gamepad1.left_stick_y;
        robot.frontrightPower = gamepad1.right_stick_y;
        robot.backrightPower = gamepad1.right_stick_y;

        // zero the powers to each servo
        robot.zeroServoPower();

        // sideways driving will override the tank drive (only if it is being used though)
        if((gamepad1.dpad_right || gamepad1.dpad_left) || (gamepad1.dpad_up || gamepad1.dpad_down))
        {
            if (gamepad1.dpad_left) {
                moveLeft(0.7);

            } else if (gamepad1.dpad_right) {
                moveRight(0.7);
            }

            if (gamepad1.dpad_right && gamepad1.dpad_up) {
                robot.backleftPower = 0.15;
                robot.frontleftPower = -0.8;
                robot.frontrightPower = 0.15;
                robot.backrightPower = -0.8;
            } else if (gamepad1.dpad_right && gamepad1.dpad_down) {
                robot.backleftPower = 0.8;
                robot.frontleftPower = -0.15;
                robot.frontrightPower = 0.8;
                robot.backrightPower = -0.15;
            } else if (gamepad1.dpad_left && gamepad1.dpad_up) {
                robot.backleftPower = -0.8;
                robot.frontleftPower = 0.15;
                robot.frontrightPower = -0.8;
                robot.backrightPower = 0.15;

            } else if (gamepad1.dpad_left && gamepad1.dpad_down) {
                robot.backleftPower = -0.15;
                robot.frontleftPower = 0.8;
                robot.frontrightPower = -0.15;
                robot.backrightPower = 0.8;
                // testing the power stuff

            }
            else if (gamepad1.dpad_down)
            {
                moveForward(0.6);
            }
            else if (gamepad1.dpad_up)
            {
                moveBackward(0.6);
            }
        }


        if(gamepad2.a)
            {
                robot.rightPower = -0.6;
                robot.leftPower = -0.6;
            }
        else if(gamepad2.y)
            {
                robot.rightPower = 0.6;
                robot.leftPower = 0.6;
            }
        if(gamepad2.dpad_up)
            {
                robot.middlePower = 1;
            }
        if(gamepad2.dpad_down)
            {
                robot.middlePower = -1;
            }


        if((gamepad2.dpad_left|| gamepad2.dpad_right) || (gamepad2.x || gamepad2.b) )
        {
            if(gamepad2.dpad_left)
            {
                robot.armPower = -0.65;

            }
            else if(gamepad2.dpad_right)
            {
                robot.armPower = 0.65;
            }
            else if(gamepad2.x)
            {
                robot.sledLeftPower = 1;
                robot.sledRightPower = 1;
            }
            else if(gamepad2.b)
            {
                robot.sledLeftPower = -1;
                robot.sledRightPower = -1;
            }
        }

        // 50% power changer
        if(gamepad1.right_bumper)
        {
            robot.backleftPower *= 0.5;
            robot.frontleftPower *= 0.5;
            robot.frontrightPower *= 0.5;
            robot.backrightPower *= 0.5;
        }


        // update the power to each thing on the robot -> should be done every time
        robot.updateMotorPower();
        robot.updateServoPower();

        // Show the elapsed game time and wheel power.
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Motors", "left (%.2f), right (%.2f)", robot.backleftPower, robot.backrightPower);
    }

    // runs once (when stop is pressed)
    @Override
    public void stop()
    {

    }

    private void moveLeft(double power)
    {
        robot.backleftPower = -power;
        robot.frontleftPower = power;
        robot.frontrightPower = -power;
        robot.backrightPower = power;
    }
    private void moveRight(double power)
    {
        robot.backleftPower = power;
        robot.frontleftPower = -power;
        robot.frontrightPower = power;
        robot.backrightPower = -power;
    }
    private void moveForward(double power)
    {
        robot.backleftPower = power;
        robot.frontleftPower = power;
        robot.frontrightPower = power;
        robot.backrightPower = power;
    }
    private void moveBackward(double power)
    {
        robot.backleftPower = -power;
        robot.frontleftPower = -power;
        robot.frontrightPower = -power;
        robot.backrightPower = -power;
    }



}

/*
TO DO:

up and down on d- pad for all directions        DONE
50 % speed for left trigger                     DONE

 */