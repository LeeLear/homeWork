/*
 * Copyright 2002-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.core;

import org.springframework.util.Assert;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * The purpose of this class is to enable capturing and passing a generic
 * {@link Type}. In order to capture the generic type and retain it at runtime,
 * you need to create a subclass as follows:
 *
 * <pre class="code">
 * ParameterizedTypeReference&lt;List&lt;String&gt;&gt; typeRef = new ParameterizedTypeReference&lt;List&lt;String&gt;&gt;() {};
 * </pre>
 *
 * <p>The resulting {@code typeReference} instance can then be used to obtain a
 * {@link Type} instance that carries parameterized type information.
 * For more information on "super type tokens" see the link to Neal Gafter's blog post.
 *
 * @author Arjen Poutsma
 * @author Rossen Stoyanchev
 * @since 3.2
 * @see <a href="http://gafter.blogspot.nl/2006/12/super-type-tokens.html">Neal Gafter on Super Type Tokens</a>
 */
public abstract class ParameterizedTypeReference<T> {

	private final Type type;


	protected ParameterizedTypeReference() {
		Class<?> parameterizedTypeReferenceSubclass = findParameterizedTypeReferenceSubclass(getClass());
		Type type = parameterizedTypeReferenceSubclass.getGenericSuperclass();
		Assert.isInstanceOf(ParameterizedType.class, type, "Type must be a parameterized type");
		ParameterizedType parameterizedType = (ParameterizedType) type;
		Assert.isTrue(parameterizedType.getActualTypeArguments().length == 1, "Number of type arguments must be 1");
		this.type = parameterizedType.getActualTypeArguments()[0];
	}

	private ParameterizedTypeReference(Type type) {
		this.type = type;
	}


	public Type getType() {
		return this.type;
	}

	@Override
	public boolean equals(Object obj) {
		return (this == obj || (obj instanceof ParameterizedTypeReference &&
				this.type.equals(((ParameterizedTypeReference<?>) obj).type)));
	}

	@Override
	public int hashCode() {
		return this.type.hashCode();
	}

	@Override
	public String toString() {
		return "ParameterizedTypeReference<" + this.type + ">";
	}


	/**
	 * Build a {@code ParameterizedTypeReference} wrapping the given type.
	 * @param type a generic type (possibly obtained via reflection,
	 * e.g. from {@link java.lang.reflect.Method#getGenericReturnType()})
	 * @return a corresponding reference which may be passed into
	 * {@code ParameterizedTypeReference}-accepting methods
	 * @since 4.3.12
	 */
	public static <T> ParameterizedTypeReference<T> forType(Type type) {
		return new ParameterizedTypeReference<T>(type) {
		};
	}

	private static Class<?> findParameterizedTypeReferenceSubclass(Class<?> child) {
		Class<?> parent = child.getSuperclass();
		if (Object.class == parent) {
			throw new IllegalStateException("Expected ParameterizedTypeReference superclass");
		}
		else if (ParameterizedTypeReference.class == parent) {
			return child;
		}
		else {
			return findParameterizedTypeReferenceSubclass(parent);
		}
	}

}
