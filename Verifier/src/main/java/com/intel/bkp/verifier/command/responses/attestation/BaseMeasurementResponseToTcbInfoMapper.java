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

package com.intel.bkp.verifier.command.responses.attestation;

import com.intel.bkp.fpgacerts.dice.tcbinfo.TcbInfoMeasurement;
import com.intel.bkp.utils.ByteBufferSafe;
import com.intel.bkp.verifier.interfaces.IMeasurementProvider;
import com.intel.bkp.verifier.interfaces.IMeasurementRecordToTcbInfoMapper;
import com.intel.bkp.verifier.model.evidence.IMeasurementRecordHeaderBuilder;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseMeasurementResponseToTcbInfoMapper<T, R extends IMeasurementProvider> {

    public List<TcbInfoMeasurement> map(R response) {
        final List<TcbInfoMeasurement> tcbInfoMeasurements = new ArrayList<>();

        final ByteBufferSafe buffer = ByteBufferSafe.wrap(response.getMeasurementRecord());
        final var builder = getBuilder();
        while (builder.canBeParsed(buffer)) {
            final var header = builder.parse(buffer).build();
            tcbInfoMeasurements.add(getMapper().map(header, buffer));
        }

        return tcbInfoMeasurements;
    }

    protected abstract IMeasurementRecordToTcbInfoMapper<T> getMapper();

    protected abstract IMeasurementRecordHeaderBuilder<T> getBuilder();
}
