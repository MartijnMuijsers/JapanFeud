package nl.martijnmuijsers.japanfeud;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import java.io.File;

public class Examiner {
	
	private static Examiner instance = null;
	public static Examiner get() {
		if (instance == null) {
			instance = new Examiner();
		}
		return instance;
	}
	
	public void loadPack() {
		if (!packLoaded) {
			Log.info("Loading pack...");
			IOUtils.createDirectory(packsFolderPath);
			String pack = Config.get().getExaminerPack().trim();
			if (pack.isEmpty()) {
				Log.info("No examiner pack (setting 'examinerPack') was defined in the configuration!");
				Server.exit();
			}
			String examinerFilename = packsFolderPath+"/"+pack+"/examiner.txt";
			File examinerFile = new File(examinerFilename);
			if (!examinerFile.exists()) {
				Log.info("Examiner pack " + pack + " has no examiner.txt!");
				Server.exit();
			}
			String question = null;
			boolean skipped = false;
			List<String> answers = null;
			for (String line : new FileLineIterable(examinerFilename)) {
				if (question == null) {
					if (!skipped) {
						skipped = true;
					} else {
						question = line;
					}
				} else {
					if (answers == null) {
						answers = new ArrayList<>();
					}
					answers.add(line);
					if (answers.size() == 6) {
						try {
							questions.add(Question.create(question, answers));
						} catch (NumberFormatException e) {
							Log.warning("Invalid question in examiner file line: " + line);
						}
						question = null;
						skipped = false;
						answers = null;
					}
				}
			}
			Log.info(questions.size() + " questions loaded!");
			packLoaded = true;
		}
	}
	
	private final String packsFolderPath = "examiner_packs";
	private boolean packLoaded = false;
	private final List<Question> questions = new ArrayList<>();
	
	public Collection<Question> getQuestions() {
		return questions;
	}
	
}
