package equations;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class PrinterOur {
    private FileOutputStream fileForPrint;

    public PrinterOur(FileOutputStream fileForPrint){
        this.fileForPrint = fileForPrint;
    }

    public boolean printInExcelFile(double[] x, double[] y, String nameList, String Schema){
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet(nameList);
        Row row0 = sheet.createRow(0);
        Cell cell = row0.createCell(0);
        cell.setCellValue(Schema);

        for ( int i = 0; i < x.length; i++){
            Row row = sheet.createRow(i+1);

            Cell cellX = row.createCell(0);
            cellX.setCellValue(x[i]);

            Cell cellY = row.createCell(1);
            cellY.setCellValue(y[i]);
        }

        try {
            wb.write(fileForPrint);
            fileForPrint.close();
        } catch (IOException e) {
            System.out.println("Проблемы с выводом в файл");
            return false;
        }
        return true;
    }

}
