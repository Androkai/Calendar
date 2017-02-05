package calendar.frontend.messages;

import calendar.backend.dateTime.Time;
import calendar.frontend.messages.MessageHandler.OutputType;

public class MessageMeta {
	boolean withPrefix;
	
	OutputType output;
	
	public MessageMeta(boolean withPrefix, OutputType output) {
		setOutputType(output);
		setWithPrefix(withPrefix);
	}
	
	public MessageMeta() {
		this(true, OutputType.CHAT);
	}
	
	public OutputType getOutputType() {
		return output;
	}
	public void setOutputType(OutputType output) {
		this.output = output;
	}
	
	public boolean isWithPrefix() {
		return withPrefix;
	}
	public void setWithPrefix(boolean withPrefix) {
		this.withPrefix = withPrefix;
	}
}
