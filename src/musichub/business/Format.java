package musichub.business;

public enum Format {
	MP3 ("mp3"), WAV ("wav"), NOFORMAT ("noformat");
	private String format;
	private Format (String format) {
		this.format = format;
	}
	public String getFormat() {
		return format;
	}
}