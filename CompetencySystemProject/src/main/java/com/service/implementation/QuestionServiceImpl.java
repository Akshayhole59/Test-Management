package com.service.implementation;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entity.Question;
import com.exception.CategoryNotFoundException;
import com.exception.QuestionNotFoundException;
import com.repository.CategoryRepository;
import com.repository.QuestionRepository;
import com.service.QuestionService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class QuestionServiceImpl implements QuestionService {
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;

    @Override
    public List<Question> getAllQuestions() {
        log.info("Fetching all questions");
        return questionRepository.findAll();
    }

    @Override
    public Question getQuestionById(Long id) {
        log.info("Fetching question by id: {}", id);
        return questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException("Question not found with id: " + id, null));
    }

    @Override
    public Question saveQuestion(Question question) {
        log.info("Saving question: {}", question);
        if(!categoryRepository.existsById(question.getCategory().getCategory_id())) {
        	throw new CategoryNotFoundException("Category not found with id: " + question.getCategory().getCategory_id());
        }
        return questionRepository.save(question);
    }

    @Override
    public void deleteQuestion(Long id) {
        log.info("Deleting question with id: {}", id);
        if (!questionRepository.existsById(id)) {
            throw new QuestionNotFoundException("Question not found with id: " + id, null);
        }
        questionRepository.deleteById(id);
    }

    @Override
    public List<Question> importQuestionsFromExcel(InputStream excelInputStream) throws IOException {
        Workbook workbook = WorkbookFactory.create(excelInputStream);
        List<Question> importedQuestions = new ArrayList<>();
 
        Sheet sheet = workbook.getSheetAt(0); 
        for (Row row : sheet) {
            Question question = new Question();
            question.setContent(getCellValue(row.getCell(0)));
            question.setOption1(getCellValue(row.getCell(1)));
            question.setOption2(getCellValue(row.getCell(2)));
            question.setOption3(getCellValue(row.getCell(3)));
            question.setOption4(getCellValue(row.getCell(4)));
            question.setAnswer(getCellValue(row.getCell(5)));
            question.setMarks(getCellValue(row.getCell(6)));
 
            importedQuestions.add(questionRepository.save(question));
        }
 
        return importedQuestions;
    }
 
    private String getCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }
 
        cell.setCellType(CellType.STRING);
        return cell.getStringCellValue();
    }
}
   

