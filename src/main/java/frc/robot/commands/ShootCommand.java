package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.IOConstants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.Flywheel;
import frc.robot.subsystems.IntakeClass;
import frc.robot.subsystems.Loader;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

public class ShootCommand extends Command{
    private final Command m_shootSequenceCommand;

    public ShootCommand(DriveSubsystem driveSubsystem, IntakeClass intake, Loader loader, Flywheel flywheel){
        m_shootSequenceCommand = Commands.deadline(
            Commands.sequence(
                Commands.waitSeconds(3),
                // Commands.waitUntil(supplier),
                Commands.deadline(
                    Commands.waitSeconds(10),
                    intake.runIntakeCommand(),
                    loader.runToFlywheelCommand())
            ),
            Commands.sequence(
                flywheel.increaseShooterSpeedCommand(),
                flywheel.runShooterCommand()
            )
        );
    }

    @Override
    public void initialize() {
        m_shootSequenceCommand.initialize();
    }

    @Override
    public void execute() {
        m_shootSequenceCommand.execute();
    }

    @Override
    public void end(boolean interrupted) {
        m_shootSequenceCommand.end(interrupted);
    }

    @Override
    public boolean isFinished() {
        return m_shootSequenceCommand.isFinished();
    }

}
