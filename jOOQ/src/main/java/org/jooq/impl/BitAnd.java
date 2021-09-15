/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Other licenses:
 * -----------------------------------------------------------------------------
 * Commercial licenses for this work are available. These replace the above
 * ASL 2.0 and offer limited warranties, support, maintenance, and commercial
 * database integrations.
 *
 * For more information, please visit: http://www.jooq.org/licenses
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
package org.jooq.impl;

import static org.jooq.impl.DSL.*;
import static org.jooq.impl.Internal.*;
import static org.jooq.impl.Keywords.*;
import static org.jooq.impl.Names.*;
import static org.jooq.impl.SQLDataType.*;
import static org.jooq.impl.Tools.*;
import static org.jooq.impl.Tools.BooleanDataKey.*;
import static org.jooq.impl.Tools.DataExtendedKey.*;
import static org.jooq.impl.Tools.DataKey.*;
import static org.jooq.SQLDialect.*;

import org.jooq.*;
import org.jooq.Record;
import org.jooq.conf.*;
import org.jooq.impl.*;
import org.jooq.tools.*;

import java.util.*;


/**
 * The <code>BIT AND</code> statement.
 */
@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
final class BitAnd<T extends Number>
extends
    AbstractField<T>
{

    private final Field<T> arg1;
    private final Field<T> arg2;

    BitAnd(
        Field<T> arg1,
        Field<T> arg2
    ) {
        super(
            N_BIT_AND,
            allNotNull((DataType) dataType(INTEGER, arg1, false), arg1, arg2)
        );

        this.arg1 = nullSafeNotNull(arg1, INTEGER);
        this.arg2 = nullSafeNotNull(arg2, INTEGER);
    }

    // -------------------------------------------------------------------------
    // XXX: QueryPart API
    // -------------------------------------------------------------------------

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {












            case H2:
            case HSQLDB:
                ctx.visit(function(N_BITAND, getDataType(), arg1, arg2));
                break;

            case FIREBIRD:
                ctx.visit(function(N_BIN_AND, getDataType(), arg1, arg2));
                break;

            default:
                ctx.sql('(');
                Expression.<Field<T>, BitAnd<T>>acceptAssociative(
                    ctx,
                    this,
                    q -> new Expression.Expr<>(q.arg1, Operators.OP_AMP, q.arg2),
                    c -> c.sql(' ')
                );
                ctx.sql(')');
                break;
        }
    }














    // -------------------------------------------------------------------------
    // The Object API
    // -------------------------------------------------------------------------

    @Override
    public boolean equals(Object that) {
        if (that instanceof BitAnd) {
            return
                StringUtils.equals(arg1, ((BitAnd) that).arg1) &&
                StringUtils.equals(arg2, ((BitAnd) that).arg2)
            ;
        }
        else
            return super.equals(that);
    }
}