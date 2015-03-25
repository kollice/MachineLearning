package com.kollice.machinelearning.chapter4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.kollice.machinelearning.share.Utility;

public class SimpleBayesian {
	private List<String[]> trainData;
	private int[] trainRespct;

	public List<String[]> getTrainData() {
		return trainData;
	}

	public void setTrainData(List<String[]> trainData) {
		this.trainData = trainData;
	}

	public int[] getTrainRespct() {
		return trainRespct;
	}

	public void setTrainRespct(int[] trainRespct) {
		this.trainRespct = trainRespct;
	}
	
	public SimpleBayesian() {
		super();
	}
	
	public int analyze(List<String[]> trainData,int[] trainRespct,String[] data) throws Exception {
		int result = -1;
		List dictList = Utility.deduplication(trainData);
		List trainList = new ArrayList<>();
		int[][] vectors = new int[trainData.size()][];
		int index = 0;
		for (String[] item : trainData) {
//			int[] vector = Assistant.genDataVector(dictList, Arrays.asList(item));
			int[] vector = Assistant.genBagDataVector(dictList, Arrays.asList(item));
			vectors[index] = vector;
			index++;
		}

		Map trainerMap = Assistant.getTrainProbability(vectors, trainRespct);
		
//		int[] test0Vector = Assistant.genDataVector(dictList, Arrays.asList(data));
		int[] test0Vector = Assistant.genBagDataVector(dictList, Arrays.asList(data));
		result = Assistant.classfic(test0Vector, trainerMap);
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	/* ************************************************************************************************ */

	private static List<String[]> getData() {
		String[][] orgin = {
				{ "my", "dog", "has", "flea", "problems", "help", "please" },
				{ "maybe", "not", "take", "him", "to", "dog", "park", "stupid" },
				{ "my", "dalmation", "is", "so", "cute", "I", "love", "him" },
				{ "stop", "posting", "stupid", "worthless", "garbage" },
				{ "mr", "licks", "ate", "my", "steak", "how", "to", "stop",
						"him" },
				{ "quit", "buying", "worthless", "dog", "food", "stupid" } };
		List<String[]> resultList = new ArrayList<String[]>();
		for (String[] item : orgin) {
			resultList.add(item);
		}
		return resultList;
	}

	private static int[] getVector() {
		int[] result = { 0, 1, 0, 1, 0, 1 };
		return result;
	}
	
	private static void testHits() throws Exception {
		List<String[]> docList = null;
		int[] classList = new int[26];
		List<String> fullText = null;
		List<String> wordList = null;
		
		for (int i=1;i<26;i++) {
			wordList = Utility.textParse(Utility.readFileToString("C:\\Users\\yangty\\git\\MachineLearningLocal\\MachineLearning\\src\\com\\kollice\\machinelearning\\chapter4\\sample\\email\\spam\\") + i + ".txt");
			docList.add((String[])wordList.toArray());
			fullText = new ArrayList<String>(wordList);
			classList[i-1] = 1;
			
			wordList = Utility.textParse(Utility.readFileToString("C:\\Users\\yangty\\git\\MachineLearningLocal\\MachineLearning\\src\\com\\kollice\\machinelearning\\chapter4\\sample\\email\\ham\\") + i + ".txt");
			docList.add((String[])wordList.toArray());
			fullText = new ArrayList<String>(wordList);
			classList[i-1] = 0;
		}
		

		
			     

//			    vocabList = createVocabList(docList)#create vocabulary
//			    trainingSet = range(50); testSet=[]           #create test set
//			    for i in range(10):
//			        randIndex = int(random.uniform(0,len(trainingSet)))
//			        testSet.append(trainingSet[randIndex])
//			        del(trainingSet[randIndex])  
//			    trainMat=[]; trainClasses = []
//			    for docIndex in trainingSet:#train the classifier (get probs) trainNB0
//			        trainMat.append(bagOfWords2VecMN(vocabList, docList[docIndex]))
//			        trainClasses.append(classList[docIndex])
//			    p0V,p1V,pSpam = trainNB0(array(trainMat),array(trainClasses))
//			    errorCount = 0
//			    for docIndex in testSet:        #classify the remaining items
//			        wordVector = bagOfWords2VecMN(vocabList, docList[docIndex])
//			        if classifyNB(array(wordVector),p0V,p1V,pSpam) != classList[docIndex]:
//			            errorCount += 1
//			            print "classification error",docList[docIndex]
//			    print 'the error rate is: ',float(errorCount)/len(testSet)
	}

	public static void main(String[] args) {
		try {
//			List<String[]> trainData = getData();
//			int[] trainRespct = getVector();
//			SimpleBayesian simpleBayesian = new SimpleBayesian();
//			String[] testFor0 = { "love", "my", "dalmation" };
//			System.out.println("预期值为：0 <-------------------------> 实际值为："
//					+ simpleBayesian.analyze(trainData, trainRespct, testFor0));
//
//			String[] testFor1 = { "stupid", "garbage" };
//			System.out.println("预期值为：1 <-------------------------> 实际值为："
//					+ simpleBayesian.analyze(trainData, trainRespct, testFor1));
			
			testHits();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
