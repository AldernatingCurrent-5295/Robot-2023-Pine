
package frc.robot.subsystems;

import edu.wpi.first.apriltag.AprilTagDetection;
import edu.wpi.first.apriltag.AprilTagDetector;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.CvSink;
import edu.wpi.first.cscore.CvSource;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class Camera extends SubsystemBase {
  Thread m_visionThread;

  Map<Integer, AprilTagDetection> tagMap;

  private final int h = 480;
  private final int w = 640;

  public Camera() {

    this.tagMap = new HashMap<Integer, AprilTagDetection>() ;

    m_visionThread =
        new Thread(() -> AprilTagDetectionThreadProc());
    m_visionThread.setDaemon(true);
    m_visionThread.start();
  }

  @Override
  public void periodic() {
    // SmartDashboard.putNumber("ID 1 X", getTagCX(1));
  }

  private void AprilTagDetectionThreadProc() {
    UsbCamera camera = CameraServer.startAutomaticCapture();
    camera.setResolution(w, h);

    CvSink cvSink = CameraServer.getVideo();
    CvSource outputStream = CameraServer.putVideo("Detect", 640, 480);

    AprilTagDetector detector = new AprilTagDetector();
    AprilTagDetector.Config cfg = new AprilTagDetector.Config();

    detector.setConfig(cfg);
    detector.addFamily("tag16h5");

    Mat mat = new Mat();
    Mat grayMat = new Mat();
    ArrayList<Integer> tags = new ArrayList<>();

    Scalar outlineColor = new Scalar(0, 255, 0);
    Scalar xColor = new Scalar(0, 0, 255);

    while (!Thread.interrupted()) {
      if (cvSink.grabFrame(mat) == 0) {
        outputStream.notifyError(cvSink.getError());
        continue;
      }

      Imgproc.cvtColor(mat, grayMat, Imgproc.COLOR_BGR2GRAY);

      AprilTagDetection[] detections = detector.detect(grayMat);
      tags.clear();
      for (AprilTagDetection detection : detections) {
        tags.add(detection.getId());
        tagMap.put(detection.getId(), detection);
        
        for (int i = 0; i <= 3; i++) {
          var j = (i + 1) % 4;
          var pt1 = new Point(detection.getCornerX(i), detection.getCornerY(i));
          var pt2 = new Point(detection.getCornerX(j), detection.getCornerY(j));
          Imgproc.line(mat, pt1, pt2, outlineColor, 2);
        }

        var cx = detection.getCenterX();
        var cy = detection.getCenterY();
        var ll = 10;
        Imgproc.line(mat, new Point(cx - ll, cy), new Point(cx + ll, cy), xColor, 2);
        Imgproc.line(mat, new Point(cx, cy - ll), new Point(cx, cy + ll), xColor, 2);
        Imgproc.putText(mat, Integer.toString(detection.getId()), new Point(cx + ll, cy), Imgproc.FONT_HERSHEY_COMPLEX, 1, xColor, 3);
      }

      SmartDashboard.putString("tag", tags.toString());

      outputStream.putFrame(mat);
    }
    detector.close();
  }

  public double getTagCX(int tagID) {
    AprilTagDetection tag = tagMap.getOrDefault(tagID, null);
    if (tag == null) {
      return 99.213; // Just a random number to id nulls
    }
    return tag.getCenterX() / w;
  }

  public double getTagCY(int tagID) {
    AprilTagDetection tag = tagMap.getOrDefault(tagID, null);
    if (tag == null) {
      return 99.213;
    }
    return tag.getCenterY() / h;  
  }
}