package jsp.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import jsp.member.model.vo.MemberVo;

public class PensionExcelWriter {
	public PensionExcelWriter() {
	}

	// 회원 리스트 출력
	public boolean memberListWriter(ArrayList<MemberVo> list) {
		boolean result = false;
		if(list != null && !list.isEmpty()) {
			XSSFWorkbook workbook = new XSSFWorkbook();
			// 워크시트 생성
			XSSFSheet sheet = workbook.createSheet();
			// 행 생성
			XSSFRow row = sheet.createRow(0);
			// 쎌 생성
			XSSFCell cell;

			// 헤더 정보 구성
			cell = row.createCell(0);
			cell.setCellValue("아이디");

			cell = row.createCell(1);
			cell.setCellValue("비밀번호");

			cell = row.createCell(2);
			cell.setCellValue("생년월일");

			cell = row.createCell(3);
			cell.setCellValue("이메일");
			
			cell = row.createCell(4);
			cell.setCellValue("휴대폰번호");
			
			cell = row.createCell(5);
			cell.setCellValue("성별");
			
			cell = row.createCell(6);
			cell.setCellValue("이름");
			
			cell = row.createCell(7);
			cell.setCellValue("가입일");
			
			cell = row.createCell(8);
			cell.setCellValue("주소");

			// 리스트의 size 만큼 row를 생성
			
			for(int rowIdx=0; rowIdx < list.size(); rowIdx++) {
				MemberVo vo = list.get(rowIdx);

				// 행 생성 -> 리스트 사이즈 +1 
				row = sheet.createRow(rowIdx+1);

				cell = row.createCell(0);
				cell.setCellValue(vo.getMbId());

				cell = row.createCell(1);
				cell.setCellValue(vo.getMbPwd());

				cell = row.createCell(2);
				cell.setCellValue(vo.getMbBirth());

				cell = row.createCell(3);
				cell.setCellValue(vo.getMbEmail());
				
				cell = row.createCell(4);
				cell.setCellValue(vo.getMbPhone());
				
				cell = row.createCell(5);
				cell.setCellValue(vo.getMbGender());
				
				cell = row.createCell(6);
				cell.setCellValue(vo.getMbName());
				
				cell = row.createCell(7);
				cell.setCellValue(vo.getMbEntDate());
				
				cell = row.createCell(8);
				cell.setCellValue(vo.getMbAddress());

			}

			// 입력된 내용 파일로 쓰기
			File file = new File("C:\\Users\\snugs\\git\\KH-WEB-Project-v2/test.xlsx");
			FileOutputStream fos = null;

			try {
				fos = new FileOutputStream(file);
				workbook.write(fos);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if(workbook!=null) workbook.close();
					if(fos!=null) fos.close();

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			result = true;
		}else {
			// 리스트 없으면 false;
		}

		return result;
	}

}
