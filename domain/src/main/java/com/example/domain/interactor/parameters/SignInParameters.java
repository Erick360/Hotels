package com.example.domain.interactor.parameters;

public class SignInParameters {
    public static final class Parameters {
        private String sessionProvider;
        private String accountIdToken;

        public String getSessionProvider() {
            return sessionProvider;
        }

        private Parameters(String sessionProvider, String accountIdToken){
            this.sessionProvider = sessionProvider;
            this.accountIdToken = accountIdToken;
        }

        public static Parameters Create(
        String sessionProvider, String
        accountIdToken){

            return new Parameters(sessionProvider, accountIdToken);

        }

    }
}
