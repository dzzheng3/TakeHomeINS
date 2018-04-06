package com.zheng.home.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zheng.home.data.model.Pokemon;
import com.zheng.home.data.model.Quiz;
import com.zheng.home.data.remote.PokemonApi;
import com.zheng.home.data.remote.QuizApi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class DataManager {

    private PokemonApi pokemonService;
    private QuizApi quizApi;

    @Inject
    public DataManager(PokemonApi pokemonService, QuizApi quizApi) {
        this.pokemonService = pokemonService;
        this.quizApi = quizApi;
    }

    public Single<List<String>> getPokemonList(int limit) {
        return pokemonService
                .getPokemonList(limit)
                .toObservable()
                .flatMapIterable(namedResources -> namedResources.getResults())
                .map(namedResource -> namedResource.getName())
                .toList();
    }

    public Single<Pokemon> getPokemon(String name) {
        return pokemonService.getPokemon(name);
    }

    public Single<String> getResponse() {
        return quizApi.getQuizItemList();
    }

    public Quiz getRandomQuiz(List<Quiz> quizList) {
        Random random = new Random();
        int i = random.nextInt(quizList.size());
        return quizList.get(i);
    }

    public int getQuizAnswer(Quiz quiz) {
        List<String> answers = quiz.getImages();
        String correctAnswer = answers.get(0);
        Collections.shuffle(answers);
        return answers.indexOf(correctAnswer);
    }

    public List<Quiz> getQuizList(String json) {
        JsonParser jsonParser = new JsonParser();
        JsonElement parse = jsonParser.parse(json);
        JsonObject asJsonObject = parse.getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> entries = asJsonObject.entrySet();
        List<Quiz> quizList = new ArrayList<>();
        for (Map.Entry<String, JsonElement> entry : entries) {
            String key = entry.getKey();
            JsonElement value = entry.getValue();
            JsonArray asJsonArray = value.getAsJsonArray();
            List<String> images = new ArrayList<>();
            for (int i = 0; i < asJsonArray.size(); i++) {
                images.add(asJsonArray.get(i).getAsString());
            }
            quizList.add(new Quiz(key, images));
        }
        return quizList;
    }
}
