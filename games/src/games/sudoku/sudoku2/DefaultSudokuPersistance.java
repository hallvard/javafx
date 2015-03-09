package games.sudoku.sudoku2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultSudokuPersistance  implements ISudokuPersistence {

	private static final String SEPARATOR = ",";
	
	@Override
	public void load(ISudoku sudoku, InputStream inputStream)
			throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		
		String level = reader.readLine();
		
		
		List<Edit> edits = reader.lines().map(line -> {
			String[] parts = line.split(SEPARATOR);
			int x = Integer.parseInt(parts[0]);
			int y = Integer.parseInt(parts[1]);
			int value = Integer.parseInt(parts[2]);
			return new Edit(x, y, null, value);
		})
		.collect(Collectors.toList());
		
		sudoku.init(level, edits);
	}

	@Override
	public void save(ISudoku sudoku, OutputStream outputStream)
			throws IOException {
		PrintWriter writer = new PrintWriter(outputStream);
		
		List<Edit> edits = new ArrayList<Edit>();
		
		for(int y = 0; y < ISudoku.BOARD_SIZE; y++){
			for(int x = 0; x < ISudoku.BOARD_SIZE; x++){
				Integer value = sudoku.getCellValue(x, y);
				
				if(value != null){
					if(sudoku.isAssignable(x, y)){
						edits.add(new Edit(x, y, null, value));
						writer.write(".");
					} 
					else{
						writer.write(value.toString());
					}
				}
				else{
					writer.write(".");
				}
			}
		}
		
		for(Edit edit: edits){
			writer.write(String.format(System.lineSeparator()+"%d" + SEPARATOR + "%d" + SEPARATOR + "%d", edit.getX(), edit.getY(), edit.getNewValue()));
		}
		
		writer.flush();
	}

}
