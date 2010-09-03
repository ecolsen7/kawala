/**
 * Copyright 2010 KaChing Group Inc. Licensed under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.kaching.platform.common;

import static java.lang.String.format;

import java.util.Iterator;

import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;

/**
 * Option<T> := None | Some<T>.
 */
public abstract class Option<T> implements Iterable<T> {

  /** The singleton representing none.
   */
  private final static Option<?> NONE = new Option<Object>() {

    @Override
    public Iterator<Object> iterator() {
      return new AbstractIterator<Object>() {
        @Override
        protected Object computeNext() {
          return endOfData();
        }
      };
    }

    @Override
    public Object getOrElse(Object defaultValue) {
      return defaultValue;
    }

    @Override
    public Object getOrElse(Thunk<Object> defaultValue) {
      return defaultValue.get();
    }

    @Override
    public Object getOrThrow() {
      return getOrThrow(new IllegalArgumentException());
    }

    @Override
    public Object getOrThrow(String message) {
      return getOrThrow(new IllegalArgumentException(message));
    }

    @Override
    public <E extends Throwable> Object getOrThrow(E e) throws E {
      throw e;
    }

    @Override
    public boolean isEmpty() {
      return true;
    }

    @Override
    public int hashCode() {
      return 0;
    }

    @Override
    public boolean equals(Object that) {
      return this == that;
    }

    @Override
    public String toString() {
      return "Option.None";
    }

  };

  /** The object representing some result.
   */
  private final static class Some<U> extends Option<U> {

    private final U u;

    private Some(U u) {
      Preconditions.checkNotNull(u);
      this.u = u;
    }

    @Override
    public Iterator<U> iterator() {
      return new AbstractIterator<U>() {
        private boolean produce = true;
        @Override
        protected U computeNext() {
          if (produce) {
            produce = false;
            return u;
          } else {
            return endOfData();
          }
        }
      };
    }

    @Override
    public U getOrElse(U defaultValue) {
      return u;
    }

    @Override
    public U getOrElse(Thunk<U> defaultValue) {
      return u;
    }

    @Override
    public U getOrThrow() {
      return u;
    }

    @Override
    public U getOrThrow(String message) {
      return u;
    }

    @Override
    public <E extends Throwable> U getOrThrow(E e) throws E {
      return u;
    }

    @Override
    public boolean isEmpty() {
      return false;
    }

    @Override
    public int hashCode() {
      return u.hashCode();
    }

    @Override
    public boolean equals(Object that) {
      if (this == that) {
        return true;
      }
      if (!(that instanceof Some<?>)) {
        return false;
      }
      return this.u.equals(((Some<?>) that).u);
    }

    @Override
    public String toString() {
      return format("Option.Some(%s)", u);
    }

  }

  /**
   * If the option is nonempty returns its value, otherwise returns
   * {@code defaultValue}. Note that {@code defaultValue} is eagerly evaluated.
   */
  public abstract T getOrElse(T defaultValue);

  /**
   * If the option is nonempty returns its value, otherwise returns
   * {@code defaultValue}. The {@code defaultValue} is lazily evaluated.
   */
  public abstract T getOrElse(Thunk<T> defaultValue);

  /**
   * Gets the value of this option. If this is a Some(T) the value is returned,
   * otherwise an {@link IllegalArgumentException} is thrown.
   */
  public abstract T getOrThrow();

  /**
   * Gets the value of this option. If this is a Some(T) the value is returned,
   * otherwise an {@link IllegalArgumentException} is thrown with the specifed
   * message.
   */
  public abstract T getOrThrow(String message);

  /**
   * Returns its value if not empty, otherwise throws {@code e}.
   */
  public abstract <E extends Throwable> T getOrThrow(E e) throws E;

  /**
   * Returns {@code true} if the option is the {@code None} value.
   */
  public abstract boolean isEmpty();

  /**
   * Returns {@code true} if the option is a {@code Some(...)}.
   */
  public boolean isDefined() {
    return !isEmpty();
  }

  /**
   * Gets the none object for the given type.
   *
   * Note: using a freely parameterized type {@code T} with an unchecked warning
   * allows to simulate a bottom type in Java.
   */
  @SuppressWarnings("unchecked")
  public static <T> Option<T> none() {
    return (Option<T>) NONE;
  }

  /**
   * Gets the some object wrapping the given value.
   */
  public static <T> Option<T> some(T t) {
    return new Option.Some<T>(t);
  }

  /**
   * Wraps anything.
   *
   * @param <T>
   * @param t
   * @return if null, none(), some(t) otherwise
   */
  public static <T> Option<T> of(T t) {
    return t == null ? Option.<T>none() : some(t);
  }

}
