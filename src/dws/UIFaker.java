package dws;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by dsayles on 2/24/16.
 */
public class UIFaker {
    public static int presentChoices(String[] choices) {
        int result = 0;

        System.out.println("Please select from the following:");
        for (int i = 0; i<choices.length; i++) {
            System.out.println("\t"+i+") "+ choices[i]);
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            result = Integer.parseInt(br.readLine());
        } catch (IOException e) {
            System.out.println(e);
        }

        return result;
    }
}
