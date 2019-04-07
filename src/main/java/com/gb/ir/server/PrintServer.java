package com.gb.ir.server;

import org.apache.pdfbox.tools.PrintPDF;

import javax.print.*;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PrintQuality;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.FileInputStream;
import java.io.IOException;

public class PrintServer {
    public static void main(String[] args) throws Exception {
        printAllPrinterName();
        print("bbb.jpg", "Microsoft Print to PDF");
//        printToPDF("bbb.jpg", "Microsoft Print to PDF");

    }

    /**
     * 功能描述:调用指定打印机打印图片
     * filePath: 打印文件的位置
     * printerName: 打印机名称
     */
    public static void print(String filePath, String printerName) throws Exception {

        PrintService[] printServices = PrinterJob.lookupPrintServices();
        if (printServices.length == 0) {
            System.out.println("没有可用的打印机");
            return;
        }

        PrintService printService = null;
        for (int i = 0; i < printServices.length; i++) {
            String sps = printServices[i].getName();
            System.out.println("Print :" + sps);
            // 查询匹配打印机
            if (sps.contains(printerName)) {
                printService = printServices[i];
                break;
            }
        }
        if (null == printService) {
            System.out.println("没有找到打印机");
            return;
        }
        System.out.println("Print Start");
        DocFlavor dof = null;
        if (filePath.endsWith(".gif")) {
            dof = DocFlavor.INPUT_STREAM.GIF;
        } else if (filePath.endsWith(".jpg")) {
            dof = DocFlavor.INPUT_STREAM.JPEG;
        } else if (filePath.endsWith(".png")) {
            dof = DocFlavor.INPUT_STREAM.PNG;
        } else {
            dof = DocFlavor.INPUT_STREAM.AUTOSENSE;
        }
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
        pras.add(OrientationRequested.PORTRAIT);
        pras.add(new Copies(1));
        pras.add(PrintQuality.HIGH);
        DocAttributeSet das = new HashDocAttributeSet();
        // 设置打印纸张的大小（以毫米为单位）
        das.add(new MediaPrintableArea(0, 0, 210, 296, MediaPrintableArea.MM));
        FileInputStream fis = new FileInputStream(filePath);
        Doc doc = new SimpleDoc(fis, dof, das);
        DocPrintJob job = printService.createPrintJob();
        job.print(doc, pras);
        fis.close();
        System.out.println("打印成功！文件：" + filePath + "数量为：" + 1);
        System.out.println("Print End");
    }

    public static void printToPDF(String fileName, String printerName) throws PrinterException, IOException {
        PrintPDF.main(new String[]{
                "-silentPrint",//静默打印
                "-printerName", "Microsoft Print to PDF",//指定打印机名
                "-orientation", "auto|landscape|portrait",//打印方向，三种可选
                "bbb.jpg"
        });
//        PDDocument document = new PDDocument();
//        document.save("a111.pdf");
//        document.close();
    }

    public static void printAllPrinterName() {
        PrintService[] printServices = PrinterJob.lookupPrintServices();
        if (printServices.length == 0) {
            System.out.println("没有可用的打印机");
            return;
        }
        for (PrintService printService : printServices) {
            System.out.println("Printer :" + printService.getName());
        }

    }
}
