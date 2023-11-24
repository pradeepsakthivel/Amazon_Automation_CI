package com.amazon.base;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.ConcurrentRefUpdateException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.NoMessageException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.api.errors.UnmergedPathsException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

public class PushToGit extends TestUtilities {

	public void pushtogit(String report, String testcaseType) throws IOException {
		System.out.println("Inside push to GIT");
		// clone base repository into a local directory
		try {
			try {
				System.out.println("Report File Name: " + report);
				File delGit = new File("target/git");

				if (delGit.exists()) {
					System.out.println("Directory exists");
					FileUtils.deleteDirectory(delGit);
					System.out.println("File Deleted successfully");
				} else {
					System.out.println("Directory target/git doesnt exist");
				}
			} catch (Exception e1) {
				System.out.println("Directory does not exist");
				e1.printStackTrace();
			}

			/***
			 * create, modify, delete files in the repository's working directory, that is
			 * located at git.getRepository().getWorkTree() add and new and changed files to
			 * the staging area
			 **/

			git_test_repo = dev_properties.getString("GIT_AUTOMATION_TEST_REPO");
			String git_username = dev_properties.getString("GIT_USERNAME");
			String git_access_token = dev_properties.getString("GIT_ACCESS_TOKEN");

			Git git = Git.cloneRepository().setURI(git_test_repo)
					.setCredentialsProvider(new UsernamePasswordCredentialsProvider(git_username, git_access_token))
					.setDirectory(new File("target/git")).call();

			System.out.println(" Created Git Object");
			LocalDateTime myDateObj = LocalDateTime.now();
			DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
			String formattedDate = myDateObj.format(myFormatObj);

			File source = new File(output_file_path + report);
			File dest = new File("target/git/docs/" + report);

			try {
				if (source.exists()) {
					System.out.println("Output deliverable:" + report + " is present in local system");
					FileUtils.copyFile(source, dest);
				} else {
					System.out.println("\n" + "Output deliverable:" + report + "  is NOT present in local system \n");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			//If tetstcase type is "API" then upload html report outside of zip folder additionally		
			if (testcaseType.equalsIgnoreCase("API")) {
				System.out.println("Inside conditional loop");

				File source1 = new File(output_file_path + "TestAutomationReport_API_latest.html");
				File dest1 = new File("target/git/docs/" + "TestAutomationReport_API_latest.html");

				System.out.println("source1:" + output_file_path + "TestAutomationReport_API_latest.html");
				System.out.println("dest1:" + output_file_path + "TestAutomationReport_API_latest.html");

				try {
					if (source1.exists()) {
						System.out.println("Report:" + report + " is present in local system");
						FileUtils.copyFile(source1, dest1);
					} else {
						System.out.println("\n" + "Report:" + output_file_path + "TestAutomationReport_API_latest.html"
								+ "  is" + " NOT present in local system \n");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			git.add().addFilepattern(".").call();

			// commit changes to the local repository
			git.commit().setMessage("Added through the Autonmation  run on" + formattedDate).call();
			// push new commits to the base repository
			git.push().setRemote(git_test_repo)
			.setCredentialsProvider(new UsernamePasswordCredentialsProvider(git_username, git_access_token))
			.call();
			System.out.println("commited to GIT successfully");
		} catch (InvalidRemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoFilepatternException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoHeadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoMessageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnmergedPathsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConcurrentRefUpdateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WrongRepositoryStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//	public void postMessageTest() {		
	//
	//		APIUtilities apiutilities = new APIUtilities();
	//
	//		String baseUri=null;
	//	    baseUri=apiutilities.apiUrlProperties.getString("WEBHOOK_API_BASE_URI");
	//
	//		HashMap reqHeaders = new HashMap();
	//		reqHeaders.put("Content-Type","application/json");
	//
	//		HashMap requestParameter = new HashMap();
	//		try {
	//			requestParameter = apiutilities.readDataFromFile("ExecutionSummary.txt");
	////			requestParameter = apiutilities.readDataFromFile("target\\ExecutionSummary.txt");
	//			System.out.println("Requet parameter : "+requestParameter.toString());
	//		} catch (IOException e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}
	//
	//		String requestFilePath=null;
	//		String basePath=apiutilities.apiUrlProperties.getString("WEBHOOK_API_POST");
	//
	////		requestFilePath  = apiutilities.getRequestTemplatesDirectory() + File.separator + "PostMessageToTeams.json";
	//		requestFilePath  = apiutilities.getRequestTemplatesDirectory() + File.separator + "PostPayload.json";
	//
	//		System.out.println("Request File Path: "+requestFilePath);
	//		JSONObject PostMsgToTeamsRequest = apiutilities.updateRequestPostMsgToTeams(requestFilePath,requestParameter);
	//
	//		//System.out.println("PostMsgToTeamsRequest"+ PostMsgToTeamsRequest.toString());
	//
	//		Response response = new CommonRestApiCalls().createPostRequestWithBodyWithHeader(baseUri, basePath, reqHeaders, PostMsgToTeamsRequest);
	//
	//
	//	//	System.out.println("Response String: "+response.asString());
	//
	//		JsonPath postResponse = new JsonPath(response.asString());
	//
	//		String stractualResult  = response.asString();
	//		if(response.getStatusCode()==200 ) {
	//			System.out.println("Teams Message sent succefully");
	//		}else
	//		{System.out.println("Teams Message sent failed: "+response.getStatusLine());}

	//	}	

	// function to delete subdirectories and files
	public void deleteDirectory(File file) {
		// store all the paths of files and folders present
		// inside directory
		for (File subfile : file.listFiles()) {

			// if it is a subfolder,e.g Rohan and Ritik,
			// recursively call function to empty subfolder
			if (subfile.isDirectory()) {
				deleteDirectory(subfile);
			}

			// delete files and empty subfolders
			subfile.delete();
		}
	}

}
