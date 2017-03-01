package supportlibraries;

import com.qmetry.utilities.QMetryConnectionVO;

public class ExecutionParameters {

	private String runConfiguration = null;
	private int totalPassed = 0;
	private int totalFailed = 0;
	
	private QMetryConnectionVO qMetryConnection = null;
	private String executionTimeStamp = null;
	private String errorReportFileName = null;
	private String errorReportFileLocation = null;
	private String errorScreenshotFileLocation = null;
	
	/**
	 * @return the errorReportFileName
	 */
	public String getErrorReportFileName() {
		return errorReportFileName;
	}

	/**
	 * @param errorReportFileName the errorReportFileName to set
	 */
	public void setErrorReportFileName(String errorReportFileName) {
		this.errorReportFileName = errorReportFileName;
	}

	/**
	 * @return the errorReportFileLocation
	 */
	public String getErrorReportFileLocation() {
		return errorReportFileLocation;
	}

	/**
	 * @param errorReportFileLocation the errorReportFileLocation to set
	 */
	public void setErrorReportFileLocation(String errorReportFileLocation) {
		this.errorReportFileLocation = errorReportFileLocation;
	}

	/**
	 * @return the executionTimeStamp
	 */
	public String getExecutionTimeStamp() {
		return executionTimeStamp;
	}

	/**
	 * @param executionTimeStamp the executionTimeStamp to set
	 */
	public void setExecutionTimeStamp(String executionTimeStamp) {
		this.executionTimeStamp = executionTimeStamp;
	}

	/**
	 * @return the errorScreenshotFileLocation
	 */
	public String getErrorScreenshotFileLocation() {
		return errorScreenshotFileLocation;
	}

	/**
	 * @param errorScreenshotFileLocation the errorScreenshotFileLocation to set
	 */
	public void setErrorScreenshotFileLocation(String errorScreenshotFileLocation) {
		this.errorScreenshotFileLocation = errorScreenshotFileLocation;
	}

	/**
	 * @return the qMetryConnection
	 */
	public QMetryConnectionVO getQMetryConnection() {
		return qMetryConnection;
	}

	/**
	 * @param qMetryConnection the qMetryConnection to set
	 */
	public void setQMetryConnection(QMetryConnectionVO qMetryConnection) {
		this.qMetryConnection = qMetryConnection;
	}

	/**
	 * @return the runConfiguration
	 */
	public String getRunConfiguration() {
		return runConfiguration;
	}

	/**
	 * @param runConfiguration
	 *            the runConfiguration to set
	 */
	public void setRunConfiguration(String runConfiguration) {
		this.runConfiguration = runConfiguration;
	}

	/**
	 * @return the totalPassed
	 */
	public int getTotalPassed() {
		return totalPassed;
	}

	/**
	 * Increment passed count by 1
	 */
	public void incrementPassedCount() {
		totalPassed++;
	}

	/**
	 * @return the totalFailed
	 */
	public int getTotalFailed() {
		return totalFailed;
	}

	/**
	 * Increment passed count by 1
	 */
	public void incrementFailedCount() {
		totalFailed++;
	}
}
