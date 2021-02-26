package runconfig;

	public class clientMessage {
        private String from;
        private String to;
        private String content;
	    public clientMessage() {};
	    public void setFrom( String from ) {
		    this.from = from;
		}
		public void setTo( String to ) {
		    this.to = to;
		}
		public void setContent( String content ) {
		    this.content = content;
		}
		public String getFrom() {
		    return from;
		}
		public String getTo() {
		    return to;
		}
		public String getContent() {
		    return content;
		}
	}
