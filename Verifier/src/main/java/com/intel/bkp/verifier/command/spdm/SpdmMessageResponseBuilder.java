/*
 * This project is licensed as below.
 *
 * **************************************************************************
 *
 * Copyright 2020-2022 Intel Corporation. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER
 * OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * **************************************************************************
 *
 */

package com.intel.bkp.verifier.command.spdm;


import com.intel.bkp.utils.ByteBufferSafe;
import lombok.AccessLevel;
import lombok.Getter;

import java.nio.ByteBuffer;

@Getter(AccessLevel.PACKAGE)
public class SpdmMessageResponseBuilder {

    private SpdmHeader header = new SpdmHeader();
    private byte[] payload = new byte[0];

    public SpdmMessageResponseBuilder parse(byte[] message) {
        final ByteBufferSafe buffer = ByteBufferSafe.wrap(message);
        header = new SpdmHeaderBuilder().parse(buffer).build();
        payload = buffer.getRemaining();
        return this;
    }

    public SpdmMessageResponseBuilder parse(ByteBuffer message) {
        final byte[] messageBytes = new byte[message.remaining()];
        message.get(messageBytes);
        parse(messageBytes);
        return this;
    }

    public SpdmMessageResponse build() {
        final var message = new SpdmMessageResponse();
        message.setHeader(header);
        message.setPayload(payload);
        return message;
    }
}
