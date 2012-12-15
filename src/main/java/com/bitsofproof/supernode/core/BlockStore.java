/*
 * Copyright 2012 Tamas Blummer tamas@bitsofproof.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bitsofproof.supernode.core;

import java.util.List;

import com.bitsofproof.supernode.model.Blk;
import com.bitsofproof.supernode.model.Tx;
import com.bitsofproof.supernode.model.TxIn;
import com.bitsofproof.supernode.model.TxOut;

public interface BlockStore
{
	public void cache (int cacheSize) throws ValidationException;

	public String getPreviousBlockHash (String hash);

	public List<String> getLocator ();

	public List<TxOut> getUnspentOutput (List<String> addresses);

	public List<TxIn> getSpent (List<String> addresses);

	public List<TxOut> getReceived (List<String> addresses);

	public void storeBlock (Blk b) throws ValidationException;

	public String getHeadHash ();

	public void resetStore (Chain chain) throws TransactionValidationException;

	public Blk getBlock (String hash);

	public Tx getTransaction (String hash);

	public boolean isStoredBlock (String hash);

	public void validateTransaction (Tx tx) throws ValidationException;

	public long getChainHeight ();

	public boolean isEmpty ();

	public List<String> getInventory (List<String> locator, String last, int limit);
}