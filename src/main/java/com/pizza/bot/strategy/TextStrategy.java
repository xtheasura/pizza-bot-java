package main.java.com.pizza.bot.strategy;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import main.java.com.pizza.bot.Calculator;
import main.java.com.pizza.bot.CommonMessages;
import main.java.com.pizza.bot.MongoDB;
import main.java.com.pizza.bot.OrderState;
import main.java.com.pizza.bot.PizzaStore;

public class TextStrategy implements Strategy {

    @Override
    public SendMessage getResponse(Update update) {
        String chatId = String.valueOf(update.getMessage().getChatId());

        SendMessage response = new SendMessage();

        response.setChatId(chatId);
        response.setText(CommonMessages.UNKNOWN_RESPONSE);

        String textUpdate = update.getMessage().getText().trim();

        if (textUpdate.equalsIgnoreCase("/start")) {

            if(!MongoDB.userExists(chatId)){
                MongoDB.insertNewUserId(chatId);
            }

            response.setText("Welcome to Pizza Domain Please select a pizza");

            // First create the keyboard
            List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

            //Then we create the buttons' row
            List<InlineKeyboardButton> buttonsRow = new ArrayList<>();

            for (String pizza : PizzaStore.PIZZA_TYPE_LIST) {

                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(pizza);
                button.setCallbackData(pizza);
                buttonsRow.add(button);
            }

            //We add the newly created buttons row that contains the yes button to the keyboard
            keyboard.add(buttonsRow);

            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            inlineKeyboardMarkup.setKeyboard(keyboard);

            response.setReplyMarkup(inlineKeyboardMarkup);

            return response;
        }

        if (MongoDB.getFieldValue(MongoDB.ORDER_STATE, chatId).equalsIgnoreCase(OrderState.ADDRESS.toString())) {
            MongoDB.updateField(MongoDB.ADDRESS, textUpdate, chatId);
            MongoDB.updateField(MongoDB.ORDER_STATE, OrderState.PAYMENT.toString(), chatId);

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("The final total price to be paid is going to be\n\n$").append(Calculator.getFinalPrice(chatId)).append("\n\nTo get back to the main menu, tap /Start");
            response.setText(stringBuilder.toString());
        }

        return response;
    }
}