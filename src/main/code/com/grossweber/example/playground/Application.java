package com.grossweber.example.playground;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Named;

public class Application {
  static class Module extends AbstractModule {
    @Override
    protected void configure() {
      bind(Integer.class)
          .annotatedWith(Names.named("answer"))
          .toInstance(42);
    }
  }

  public static void main(final @NotNull String[] args) {
    final Injector injector = Guice.createInjector(new Module());
    final Application app = injector.getInstance(Application.class);
    app.run();
  }

  private int answer;

  @Inject
  Application(@Named("answer") int answer) {
    this.answer = answer;
  }

  private void run() {
    System.out.println(answer);
  }
}
