package br.com.finalcraft.fancychat.fancytextchat;

import br.com.finalcraft.fancychat.placeholders.PlaceHolderIntegration;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class FancyText {

    public String text = "";
    public String hoverText = null;
    public String runCommandActionText = null;
    public String suggestCommandAction = null;

    public FancyText parsePlaceholdersAndClone(CommandSender commandSender){
        FancyText cloneFancyText = new FancyText();
        Player player = null;
        if (commandSender instanceof Player){
            player = (Player) commandSender;
        }
        cloneFancyText.text = PlaceHolderIntegration.parsePlaceholder(player,text);
        if (this.hoverText != null) cloneFancyText.hoverText = PlaceHolderIntegration.parsePlaceholder(player,this.hoverText);
        if (this.runCommandActionText != null) cloneFancyText.runCommandActionText = PlaceHolderIntegration.parsePlaceholder(player,this.runCommandActionText);
        if (this.suggestCommandAction != null) cloneFancyText.suggestCommandAction = PlaceHolderIntegration.parsePlaceholder(player,this.suggestCommandAction);
        return cloneFancyText;
    }

    @Override
    public FancyText clone(){
        FancyText cloneFancyText = new FancyText();
        cloneFancyText.text = this.text;
        cloneFancyText.hoverText = this.hoverText;
        cloneFancyText.runCommandActionText = this.runCommandActionText;
        cloneFancyText.suggestCommandAction = this.suggestCommandAction;
        return cloneFancyText;
    }


    public FancyText() {
    }

    public FancyText(String text) {
        this.text = text;
    }

    public FancyText(List<String> textList) {
        this.text = String.join("\n", textList);
    }

    public FancyText(String text, String hoverText) {
        this.text = text;
        this.hoverText = hoverText;
    }

    public FancyText(String text, String hoverText, String runCommandActionText) {
        this.text = text;
        this.hoverText = hoverText;
        this.runCommandActionText = runCommandActionText;
    }

    public FancyText(String text, String hoverText, String commandActionText, boolean suggestionCommand) {
        this.text = text;
        this.hoverText = hoverText;
        if (suggestionCommand){
            this.suggestCommandAction = commandActionText;
        }else {
            this.runCommandActionText = commandActionText;
        }
    }

    /**
     *   Seta o texto dessa FancyMessage, por padrão, o texto é vazio "";
     */
    public FancyText setText(String text) {
        this.text = text;
        return this;
    }
    public FancyText setText(List<String> textList){
        this.text = String.join("\n", textList);
        return this;
    }

    /**
     *   Seta o texto dessa FancyMessage, por padrão, o texto é vazio "";
     */
    public FancyText addText(String text) {
        this.text += text;
        return this;
    }
    public FancyText addText(List<String> textList){
        this.text += String.join("\n", textList);
        return this;
    }

    /**
     *   Seta o a HoverMessage dessa FancyMessage;
     */
    public FancyText setHoverText(String hoverText) {
        this.hoverText = hoverText;
        return this;
    }
    public FancyText setHoverText(List<String> hoverTextList){
        this.hoverText = String.join("\n", hoverTextList);
        return this;
    }

    /**
     *   Seta o a RunCommand dessa FancyMessage;
     *   (Comando executado quando o jogador clica na mensagem)
     */
    public FancyText setRunCommandActionText(String runCommandActionText) {
        this.runCommandActionText = runCommandActionText;
        return this;
    }

    /**
     *   Seta o a SuggestCommand dessa FancyMessage;
     *   (Comando sugerido quando o jogador clica na mensagem)
     */
    public FancyText setSuggestCommandAction(String suggestCommandAction) {
        this.suggestCommandAction = suggestCommandAction;
        return this;
    }


    /**
     *   Retorna a parte do comando fancytext dessa FancyMessage!
     *
     *   Vale salientar que esse método nao retorna o comando correto do fancytext, apenas uma parte dele!
     *
     *   Use as funções do FancyText.class ao invés dessa!
     */
    public String getTellRawString(){
        String tellRaw = "{\"text\":\"" + this.text + "\"";
        if (hoverText != null){
            tellRaw = tellRaw + ",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"" + this.hoverText + "\"}";
        }
        if (runCommandActionText != null){
            tellRaw = tellRaw + ",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" + this.runCommandActionText + "\"}";
        }else if (suggestCommandAction != null){
            tellRaw = tellRaw + ",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"" + this.suggestCommandAction + "\"}";
        }
        tellRaw = tellRaw + "}";
        return tellRaw;
    }

    // ------------------------------------------------------------------------------------------------------
    // Métodos Estáticos para se entregar as mensagens
    // ------------------------------------------------------------------------------------------------------


    /**
     *   Recebe um player como argumento e uma List de FancyText
     *
     *   Monta o comando para esses FancyText
     *   e envia a mensagem para o jogador!
     */
    public static void sendTo(CommandSender commandSender, List<FancyText> texts){
        if (commandSender instanceof Player){
            String command = tellRawCommandBuilder(texts);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + commandSender.getName() + " " + command);
        }else {
            StringBuilder fullLine = new StringBuilder();
            for (FancyText fancyText : texts){
                fullLine.append(fancyText.text.replaceAll("&","§"));
            }
            commandSender.sendMessage(fullLine.toString());
        }

    }

    /**
     *   Monta o comando para esses FancyText
     *   e envia a mensagem para todos os jogadores!
     */
    public static void tellRawBroadcast(List<FancyText> texts){
        String command = tellRawCommandBuilder(texts);
        for (Player player : Bukkit.getOnlinePlayers()){
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + player.getName() + " " + command);
        }
    }

    /**
     *   Recebe uma List de FancyText e retorna
     *   o comando (String) corresponde a lista!
     */
    public static String tellRawCommandBuilder(List<FancyText> texts){
        String command = "[\"\"";
        for (FancyText aFancyTextElement : texts){
            command = command + "," + aFancyTextElement.getTellRawString();
        }
        command = command + "]";
        return command;
    }


    // ------------------------------------------------------------------------------------------------------
    // Faz a mesma coisa que os de cima, contudo, recebe itens diretamente, nao necessariamente em uma lista!
    // Um vetor simples por exemplo!
    // ------------------------------------------------------------------------------------------------------


    public static void sendTo(CommandSender commandSender, FancyText... texts){
        if (commandSender instanceof Player){
            String command = tellRawCommandBuilder(texts);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + commandSender.getName() + " " + command);
        }else {
            StringBuilder fullLine = new StringBuilder();
            for (FancyText fancyText : texts){
                fullLine.append(fancyText.text.replaceAll("&","§"));
            }
            commandSender.sendMessage(fullLine.toString());
        }
    }

    public static void tellRawBroadcast(FancyText... texts){
        String command = tellRawCommandBuilder(texts);
        for (Player player : Bukkit.getOnlinePlayers()){
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + player.getName() + " " + command);
        }
    }

    public static String tellRawCommandBuilder(FancyText... texts){
        String command = "[\"\"";
        for (FancyText aFancyTextElement : texts){
            command = command + "," + aFancyTextElement.getTellRawString();
        }
        command = command + "]";
        return command;
    }

}
