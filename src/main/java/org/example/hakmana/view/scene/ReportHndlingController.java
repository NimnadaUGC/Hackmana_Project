package org.example.hakmana.view.scene;

import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.*;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.example.hakmana.model.AllDeviceDetails;
import org.example.hakmana.model.DeviceReport;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import static java.lang.Integer.parseInt;

public class ReportHndlingController {
    private static final Logger otherErrorLogger = (Logger) LogManager.getLogger(ReportHndlingController.class);
    private static ReportHndlingController instance = null;
    private String repName;
    private String repStatus;

    public Label descriptionLabel;
    public Button downloadButton;
    public TableView<DeviceReport> reportTable;
    public TableColumn<DeviceReport, String> reportNameColumn;
    public TableColumn<DeviceReport, String> reportDetailColumn;

    private ReportHndlingController() {}

    public static ReportHndlingController getInstance() {
        if (instance == null) {
            instance = new ReportHndlingController();
        }
        return instance;
    }

    public void initialize() {
        // Initialize TableView columns
        reportNameColumn.setCellValueFactory(new PropertyValueFactory<>("reportName"));
        reportDetailColumn.setCellValueFactory(new PropertyValueFactory<>("reportDetail"));

        // Add records to the table
        ObservableList<DeviceReport> reports = FXCollections.observableArrayList(
                new DeviceReport("Active Devices Report", "This has full detail about Active devices"),
                new DeviceReport("Inactive Devices Report", "This has full detail about Inactive devices"),
                new DeviceReport("Repairing Devices Report", "This has full detail about Repairing devices"),
                new DeviceReport("Not Assigned Devices Report", "This has full detail about Not Assigned devices")
        );

        downloadButton.setDisable(true);
        reportTable.setItems(reports);
        addTableSelectionListener();
    }

    private void addTableSelectionListener() {
        reportTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<DeviceReport>() {
            @Override
            public void changed(ObservableValue<? extends DeviceReport> observable, DeviceReport oldValue, DeviceReport newValue) {
                if (newValue != null) {
                    repName = newValue.getReportName();
                    switch (repName) {
                        case "Active Devices Report":
                            repStatus = "Active";
                            break;
                        case "Inactive Devices Report":
                            repStatus = "Inactive";
                            break;
                        case "Repairing Devices Report":
                            repStatus = "Repairing";
                            break;
                        case "Not Assigned Devices Report":
                            repStatus = "Not Assigned";
                            break;
                    }
                    downloadButton.setDisable(false);
                }
            }
        });
    }

    public void downloadButtonOnAction(ActionEvent actionEvent) {
        getReport(repName, repStatus);
    }

    private void getReport(String heading, String status) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save PDF");
        fileChooser.setInitialFileName(heading + ".pdf");

        File selectedFile = fileChooser.showSaveDialog(new Stage());
        if (selectedFile != null) {
            String filePath = selectedFile.getAbsolutePath();
           try {
               PdfWriter writer = new PdfWriter(new File(filePath));
               PdfDocument pdf = new PdfDocument(writer);
               Document document = new Document(pdf);

               // Add header
               Paragraph header = new Paragraph(heading)
                       .setTextAlignment(TextAlignment.CENTER)
                       .setFontSize(16);
               document.add(header);

               // Create table
               Table table = new Table(2);
               table.setWidth(UnitValue.createPercentValue(100));

               // Add headers
               table.addHeaderCell(new Cell().add(new Paragraph("Device Name")));
               table.addHeaderCell(new Cell().add(new Paragraph("Devices Count")));

               AllDeviceDetails allDeviceDetails = new AllDeviceDetails();
               for (AllDeviceDetails dev : allDeviceDetails.getDevicesCount(status)) {
                   if (parseInt(dev.getDeviceCount()) != 0) {
                       table.addCell(new Cell().add(new Paragraph(dev.getDeviceName())));
                       table.addCell(new Cell().add(new Paragraph(dev.getDeviceCount())));
                   }
               }

               // Add table to document
               document.add(table);
               document.close();

               System.out.println("PDF created successfully!");
               openPdf(filePath);
           } catch (IOException e) {
               otherErrorLogger.error(e.getMessage());
               System.err.println("Error creating PDF: " + e.getMessage());
           }
        }
    }

    private void openPdf(String filePath) {
        try {
            File file = new File(filePath);
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            otherErrorLogger.error(e.getMessage());
            System.err.println("Error opening PDF: " + e.getMessage());
        }
    }
}
