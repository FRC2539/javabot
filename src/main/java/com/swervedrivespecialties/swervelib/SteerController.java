package com.swervedrivespecialties.swervelib;

public interface SteerController {
    // Object getDriveMotor();

    Object getSteerMotor();

    AbsoluteEncoder getSteerEncoder();

    double getReferenceAngle();

    void setReferenceAngle(double referenceAngleRadians);

    double getStateAngle();

    Double getMotorTemperature();
}
