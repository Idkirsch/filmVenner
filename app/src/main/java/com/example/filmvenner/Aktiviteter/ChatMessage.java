package com.example.filmvenner.Aktiviteter;

public class ChatMessage {
    private String text;
    private ChatMemberData memberData;
    private boolean belongsToCurrentUser;

    public ChatMessage(String text, ChatMemberData data, boolean belongsToCurrentUser) {
        this.text = text;
        this.memberData = data;
        this.belongsToCurrentUser = belongsToCurrentUser;
    }

    public String getText() {
        return text;
    }

    public ChatMemberData getMemberData() {
        return memberData;
    }

    public boolean isBelongsToCurrentUser() {
        return belongsToCurrentUser;
    }
}
