package chalmers.eda397_2016_group3.trello;

import android.util.Log;

import org.trello4j.model.Card;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by sebastianblomberg on 2016-04-26.
 */
public class CardDescriptorImpl implements CardDescriptor {
    private boolean isActive = false;
    private Date startDate, timeSpent;

    private final Card.Attachment attachment;
    public CardDescriptorImpl(Card c) {
        if(c.getAttachments() == null) {
            c.setAttachments(new ArrayList<Card.Attachment>());
        }

        if(c.getAttachments().isEmpty()) {
            attachment = c.new Attachment();
            attachment.setBytes("");
            c.getAttachments().add(attachment);
        } else {
            attachment = c.getAttachments().get(0);
        }

        parseAttachment(attachment);
    }

    private void parseAttachment(Card.Attachment attachment) {
        String[] arguments = attachment.getBytes().split("|");
        if(arguments == null || arguments.length != 3) {
            startDate = new Date(0);
            timeSpent = new Date(0);
            return;
        }

        isActive = "true".equals(arguments);
        startDate = new Date(Long.parseLong(arguments[1]));
        timeSpent = new Date(Long.parseLong(arguments[2]));
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public Date getStartDate() {
        return startDate;
    }

    @Override
    public Date getTimeSpent() {
        return timeSpent;
    }
}