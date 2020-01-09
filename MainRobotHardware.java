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

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This is NOT an opmode.
 */
public class MainRobotHardware
{
    /* Public OpMode members. */
    public DcMotor  frontleft   = null;
    public DcMotor  frontright  = null;
    public DcMotor  backleft    = null;
    public DcMotor  backright   = null;

    public CRServo  leftServo   = null;
    public CRServo  middleServo = null;
    public CRServo  rightServo  = null;
    public CRServo  armServo    = null;

    public CRServo  sledLeftServo  = null;
    public CRServo  sledRightServo   = null;


    public double frontleftPower, frontrightPower, backleftPower, backrightPower;  // motor powers
    public double armPower, leftPower, rightPower, middlePower, sledRightPower, sledLeftPower; // servo powers


    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public MainRobotHardware()
    {
    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        frontleft  = hwMap.get(DcMotor.class, "frontleft");
        frontright  = hwMap.get(DcMotor.class, "frontright");
        backleft  = hwMap.get(DcMotor.class, "backleft");
        backright  = hwMap.get(DcMotor.class, "backright");


        frontleft.setDirection(DcMotor.Direction.REVERSE);
        backleft.setDirection(DcMotor.Direction.REVERSE);
        frontright.setDirection(DcMotor.Direction.FORWARD);
        backright.setDirection(DcMotor.Direction.FORWARD);


        // Set all motors to zero power
        frontleft.setPower(0);
        frontright.setPower(0);
        backleft.setPower(0);
        backright.setPower(0);


        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        frontleft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontright.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backright.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backleft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Define and initialize ALL installed servos.
        leftServo   = hwMap.get(CRServo.class, "left");
        middleServo = hwMap.get(CRServo.class, "middle");
        rightServo  = hwMap.get(CRServo.class, "right");
        armServo    = hwMap.get(CRServo.class, "arm");
        sledRightServo = hwMap.get(CRServo.class, "sled 1");
        sledLeftServo = hwMap.get(CRServo.class, "sled 2");

        leftServo.setDirection(CRServo.Direction.FORWARD);
        rightServo.setDirection(CRServo.Direction.REVERSE);
        middleServo.setDirection(CRServo.Direction.REVERSE);
        armServo.setDirection(CRServo.Direction.FORWARD);
        sledLeftServo.setDirection(CRServo.Direction.FORWARD);
        sledRightServo.setDirection(CRServo.Direction.REVERSE);


    }

    public void updateMotorPower()
    {
        frontright.setPower(frontrightPower);
        backright.setPower(backrightPower);
        frontleft.setPower(frontleftPower);
        backleft.setPower(backleftPower);
    }
    public void updateServoPower()
    {
        armServo.setPower(armPower);
        leftServo.setPower(leftPower);
        middleServo.setPower(middlePower);
        rightServo.setPower(rightPower);
        sledRightServo.setPower(sledRightPower);
        sledLeftServo.setPower(sledLeftPower);


    }
    public void zeroServoPower()
    {
        armPower = 0;
        leftPower = 0;
        middlePower = 0;
        rightPower = 0;
        sledLeftPower = 0;
        sledRightPower = 0;
    }
 }

