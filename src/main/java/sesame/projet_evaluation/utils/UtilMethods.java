package sesame.projet_evaluation.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import sesame.projet_evaluation.payload.request.SignUpRequest;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UtilMethods {

    public static String getStringValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        cell.setCellType(CellType.STRING);
        return cell.getStringCellValue();
    }

    public static List<SignUpRequest> readExcelFile(@Valid MultipartFile file) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = sheet.iterator();

            List<SignUpRequest> signUpRequests = new ArrayList<>();

            while (iterator.hasNext()) {
                Row currentRow = iterator.next();

                // Skip header row
                if (currentRow.getRowNum() == 0) {
                    continue;
                }

                if(getStringValue(currentRow.getCell(0))== null) {
                    break;
                }
                SignUpRequest signUpRequest = new SignUpRequest();

                signUpRequest.setUsername(getStringValue(currentRow.getCell(0)));
                signUpRequest.setEmail(getStringValue(currentRow.getCell(0)));
                signUpRequest.setFirstname(getStringValue(currentRow.getCell(1)));
                signUpRequest.setLastname(getStringValue(currentRow.getCell(2)));
                signUpRequest.setIdentifiant(getStringValue(currentRow.getCell(3)));
                signUpRequest.setCodePostal(getStringValue(currentRow.getCell(4)));
                signUpRequest.setAdresse(getStringValue(currentRow.getCell(5)));

//                Set<String> roles = new HashSet<>();
//                roles.add(getStringValue(currentRow.getCell(6))); // Assuming roles are in the 9th column
//                signUpRequest.setRoles(roles);

                signUpRequest.setPassword(getStringValue(currentRow.getCell(6)));

                signUpRequests.add(signUpRequest);
            }

            return signUpRequests;
        }
    }




}
