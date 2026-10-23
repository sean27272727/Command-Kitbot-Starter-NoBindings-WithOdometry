// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.IOConstants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.Flywheel;
import frc.robot.subsystems.IntakeClass;
import frc.robot.subsystems.Loader;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

public final class Autos {
    /** Example static factory for an autonomous command. */
    public static Command exampleAuto(ExampleSubsystem subsystem) {
        return Commands.sequence(subsystem.exampleMethodCommand(), new ExampleCommand(subsystem));
    }

    /** Drive a fixed distance
     * 
     */
    public static Command driveDistance(DriveSubsystem driveSubsystem, double targetDistance) {
        return Commands.sequence(
            driveSubsystem.resetEncoderCommand(),
            driveSubsystem.arcadeDriveCommand(()->0.4, ()->0.0)
                .until(() -> driveSubsystem.getAverageDistanceMeters() > targetDistance)
                .finallyDo(() -> driveSubsystem.stopMotors())
        );
    }

    public static Command reverseDriveDistance(DriveSubsystem driveSubsystem, double targetDistance) {
        return Commands.sequence(
            driveSubsystem.resetEncoderCommand(),
            driveSubsystem.arcadeDriveCommand(()->-0.4, ()->0.0)
                .until(() -> driveSubsystem.getAverageDistanceMeters() < -targetDistance)
                .finallyDo(() -> driveSubsystem.stopMotors())
        );
    }

    // public static Command shoot(DriveSubsystem driveSubsystem, IntakeClass intake, Loader loader, Flywheel flywheel){
    //     // boolean speedEqualsTarget = (flywheel.getShooterSpeedRPM() == (double)IOConstants.kFlywheelDefaultTargetRPM);
    //     // BooleanSupplier supplier = () -> speedEqualsTarget;
    //     return Commands.deadline(
    //         Commands.sequence(
    //             reverseDriveDistance(driveSubsystem, 2.0),
    //             Commands.waitSeconds(3),
    //             // Commands.waitUntil(supplier),
    //             Commands.deadline(
    //                 Commands.waitSeconds(10),
    //                 intake.runIntakeCommand(),
    //                 loader.runToFlywheelCommand())
    //         ),
    //         Commands.sequence(
    //             flywheel.increaseShooterSpeedCommand(),
    //             flywheel.runShooterCommand()
    //         )
    //     );
    // }

    // //public static Command autoCommand(DriveSubsystem driveSubsystem, double forwardDistance, double backDistance, IntakeClass intake, Loader loader, Flywheel flywheel){
    //     return Commands.deadline(
    //         Commands.sequence(
    //             Commands.waitSeconds(2),
    //             reverseDriveDistance(driveSubsystem, forwardDistance),
    //             Commands.deadline(
    //                 Commands.waitSeconds(10),
    //                 shoot(intake, loader, flywheel))),
    //         intake.runIntakeCommand()
    //     );
    // }

    public static Command autoCommand(DriveSubsystem driveSubsystem, IntakeClass intake, Loader loader, Flywheel flywheel){
        return new ShootCommand(driveSubsystem, intake, loader, flywheel);
    }

    private Autos() {
        throw new UnsupportedOperationException("This is a utility class!");
    }
}
