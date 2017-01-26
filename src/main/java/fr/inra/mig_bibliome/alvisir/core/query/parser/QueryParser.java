/* Generated By:JavaCC: Do not edit this line. QueryParser.java */
package fr.inra.mig_bibliome.alvisir.core.query.parser;

import java.util.ArrayList;
import java.util.List;

import fr.inra.mig_bibliome.alvisir.core.query.AlvisIRAndQueryNode;
import fr.inra.mig_bibliome.alvisir.core.query.AlvisIRAndQueryNode.Operator;
import fr.inra.mig_bibliome.alvisir.core.query.AlvisIRNearQueryNode;
import fr.inra.mig_bibliome.alvisir.core.query.AlvisIRNoExpansionQueryNode;
import fr.inra.mig_bibliome.alvisir.core.query.AlvisIROrQueryNode;
import fr.inra.mig_bibliome.alvisir.core.query.AlvisIRPhraseQueryNode;
import fr.inra.mig_bibliome.alvisir.core.query.AlvisIRQueryNode;
import fr.inra.mig_bibliome.alvisir.core.query.AlvisIRRelationQueryNode;
import fr.inra.mig_bibliome.alvisir.core.query.AlvisIRTermQueryNode;
import fr.inra.mig_bibliome.alvisir.core.query.AlvisIRQueryNodeReducer;

@SuppressWarnings("all")
public class QueryParser implements QueryParserConstants {

  final public AlvisIRQueryNode query(String defaultField) throws ParseException {
  AlvisIRQueryNode result;
    result = or(defaultField, false);
    jj_consume_token(0);
    {if (true) return AlvisIRQueryNodeReducer.reduce(result);}
    throw new Error("Missing return statement in function");
  }

  final private AlvisIRQueryNode or(String field, boolean near) throws ParseException {
  AlvisIROrQueryNode result = new AlvisIROrQueryNode();
  AlvisIRQueryNode clause;
    clause = and(field, near);
                            result.addClause(clause);
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case OR:
        ;
        break;
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
      jj_consume_token(OR);
      clause = and(field, near);
                                                                                          result.addClause(clause);
    }
    {if (true) return result;}
    throw new Error("Missing return statement in function");
  }

  final private AlvisIRQueryNode and(String field, boolean near) throws ParseException {
  AlvisIRAndQueryNode result = new AlvisIRAndQueryNode();
  AlvisIRQueryNode clause;
  Operator op;
    clause = field(field, near);
                              result.addClause(Operator.AND, clause);
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case AND:
      case BUT:
      case LPAREN:
      case LBRACKET:
      case QUOTE:
      case NUMBER:
      case TEXT:
        ;
        break;
      default:
        jj_la1[1] = jj_gen;
        break label_2;
      }
      op = andOperator(near);
      clause = field(field, near);
                                                                                                                          result.addClause(op, clause);
    }
    {if (true) return result;}
    throw new Error("Missing return statement in function");
  }

  final private Operator andOperator(boolean near) throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case AND:
      jj_consume_token(AND);
            {if (true) return Operator.AND;}
      break;
    case BUT:
      jj_consume_token(BUT);
          if (near) {
            {if (true) throw new ParseException("'not' operator is not allowed inside near queries");}
          }
          {if (true) return Operator.BUT;}
      break;
    default:
      jj_la1[2] = jj_gen;
    {if (true) return Operator.AND;}
    }
    throw new Error("Missing return statement in function");
  }

  final private AlvisIRQueryNode field(String field, boolean near) throws ParseException {
  AlvisIRQueryNode result;
    if (jj_2_1(2)) {
      field = text();
      jj_consume_token(FIELD);
    if (near) {
      {if (true) throw new ParseException("field qualification is not allowed inside near queries");}
    }
    } else {
      ;
    }
    result = near(field, near);
    {if (true) return result;}
    throw new Error("Missing return statement in function");
  }

  final private AlvisIRQueryNode near(String field, boolean near) throws ParseException {
  AlvisIRQueryNode left;
  AlvisIRQueryNode right;
  int slop = -1;
  String relation = null;
    left = atom(field, near);
    label_3:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case SLOP:
        ;
        break;
      default:
        jj_la1[3] = jj_gen;
        break label_3;
      }
      jj_consume_token(SLOP);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case NUMBER:
        slop = number();
        break;
      case TEXT:
        relation = strictText();
        break;
      default:
        jj_la1[4] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      right = atom(field, true);
      if (relation == null)
            left = new AlvisIRNearQueryNode(field, slop, left, right);
          else
            left = new AlvisIRRelationQueryNode(field, relation, left, right);
          relation = null;
    }
    {if (true) return left;}
    throw new Error("Missing return statement in function");
  }

  final private AlvisIRQueryNode atom(String field, boolean near) throws ParseException {
  AlvisIRQueryNode result;
  AlvisIRPhraseQueryNode phraseQueryNode;
  String text;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case LPAREN:
      jj_consume_token(LPAREN);
      result = or(field, near);
      jj_consume_token(RPAREN);
                                                 {if (true) return result;}
      break;
    case NUMBER:
    case TEXT:
      text = text();
                {if (true) return QueryParserUtils.getTextQueryNode(field, text);}
      break;
    case QUOTE:
      jj_consume_token(QUOTE);
              phraseQueryNode = new AlvisIRPhraseQueryNode(field, 1);
      label_4:
      while (true) {
        text = text();
                                                                                       phraseQueryNode.addText(text);
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case NUMBER:
        case TEXT:
          ;
          break;
        default:
          jj_la1[5] = jj_gen;
          break label_4;
        }
      }
      jj_consume_token(QUOTE);
                                                                                                                                      {if (true) return phraseQueryNode;}
      break;
    case LBRACKET:
      jj_consume_token(LBRACKET);
      result = or(field, near);
      jj_consume_token(RBRACKET);
                                                     {if (true) return new AlvisIRNoExpansionQueryNode(result);}
      break;
    default:
      jj_la1[6] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final private String text() throws ParseException {
  Token t;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case TEXT:
      t = jj_consume_token(TEXT);
               {if (true) return QueryParserUtils.unescapeText(t.image);}
      break;
    case NUMBER:
      t = jj_consume_token(NUMBER);
                 {if (true) return t.image;}
      break;
    default:
      jj_la1[7] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final private String strictText() throws ParseException {
  Token t;
    t = jj_consume_token(TEXT);
               {if (true) return QueryParserUtils.unescapeText(t.image);}
    throw new Error("Missing return statement in function");
  }

  final private int number() throws ParseException {
  Token t;
    t = jj_consume_token(NUMBER);
                 {if (true) return Integer.parseInt(t.image);}
    throw new Error("Missing return statement in function");
  }

  private boolean jj_2_1(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_1(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(0, xla); }
  }

  private boolean jj_3R_7() {
    if (jj_scan_token(NUMBER)) return true;
    return false;
  }

  private boolean jj_3R_6() {
    if (jj_scan_token(TEXT)) return true;
    return false;
  }

  private boolean jj_3R_5() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_6()) {
    jj_scanpos = xsp;
    if (jj_3R_7()) return true;
    }
    return false;
  }

  private boolean jj_3_1() {
    if (jj_3R_5()) return true;
    if (jj_scan_token(FIELD)) return true;
    return false;
  }

  /** Generated Token Manager. */
  public QueryParserTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private Token jj_scanpos, jj_lastpos;
  private int jj_la;
  private int jj_gen;
  final private int[] jj_la1 = new int[8];
  static private int[] jj_la1_0;
  static {
      jj_la1_init_0();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x40,0x1caa0,0xa0,0x2000,0x18000,0x18000,0x1ca00,0x18000,};
   }
  final private JJCalls[] jj_2_rtns = new JJCalls[1];
  private boolean jj_rescan = false;
  private int jj_gc = 0;

  /** Constructor with InputStream. */
  public QueryParser(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public QueryParser(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new QueryParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 8; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 8; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor. */
  public QueryParser(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new QueryParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 8; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 8; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor with generated Token Manager. */
  public QueryParser(QueryParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 8; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(QueryParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 8; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      if (++jj_gc > 100) {
        jj_gc = 0;
        for (int i = 0; i < jj_2_rtns.length; i++) {
          JJCalls c = jj_2_rtns[i];
          while (c != null) {
            if (c.gen < jj_gen) c.first = null;
            c = c.next;
          }
        }
      }
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  static private final class LookaheadSuccess extends java.lang.Error { }
  final private LookaheadSuccess jj_ls = new LookaheadSuccess();
  private boolean jj_scan_token(int kind) {
    if (jj_scanpos == jj_lastpos) {
      jj_la--;
      if (jj_scanpos.next == null) {
        jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
      } else {
        jj_lastpos = jj_scanpos = jj_scanpos.next;
      }
    } else {
      jj_scanpos = jj_scanpos.next;
    }
    if (jj_rescan) {
      int i = 0; Token tok = token;
      while (tok != null && tok != jj_scanpos) { i++; tok = tok.next; }
      if (tok != null) jj_add_error_token(kind, i);
    }
    if (jj_scanpos.kind != kind) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
    return false;
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;
  private int[] jj_lasttokens = new int[100];
  private int jj_endpos;

  private void jj_add_error_token(int kind, int pos) {
    if (pos >= 100) return;
    if (pos == jj_endpos + 1) {
      jj_lasttokens[jj_endpos++] = kind;
    } else if (jj_endpos != 0) {
      jj_expentry = new int[jj_endpos];
      for (int i = 0; i < jj_endpos; i++) {
        jj_expentry[i] = jj_lasttokens[i];
      }
      jj_entries_loop: for (java.util.Iterator<?> it = jj_expentries.iterator(); it.hasNext();) {
        int[] oldentry = (int[])(it.next());
        if (oldentry.length == jj_expentry.length) {
          for (int i = 0; i < jj_expentry.length; i++) {
            if (oldentry[i] != jj_expentry[i]) {
              continue jj_entries_loop;
            }
          }
          jj_expentries.add(jj_expentry);
          break jj_entries_loop;
        }
      }
      if (pos != 0) jj_lasttokens[(jj_endpos = pos) - 1] = kind;
    }
  }

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[17];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 8; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 17; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    jj_endpos = 0;
    jj_rescan_token();
    jj_add_error_token(0, 0);
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

  private void jj_rescan_token() {
    jj_rescan = true;
    for (int i = 0; i < 1; i++) {
    try {
      JJCalls p = jj_2_rtns[i];
      do {
        if (p.gen > jj_gen) {
          jj_la = p.arg; jj_lastpos = jj_scanpos = p.first;
          switch (i) {
            case 0: jj_3_1(); break;
          }
        }
        p = p.next;
      } while (p != null);
      } catch(LookaheadSuccess ls) { }
    }
    jj_rescan = false;
  }

  private void jj_save(int index, int xla) {
    JJCalls p = jj_2_rtns[index];
    while (p.gen > jj_gen) {
      if (p.next == null) { p = p.next = new JJCalls(); break; }
      p = p.next;
    }
    p.gen = jj_gen + xla - jj_la; p.first = token; p.arg = xla;
  }

  static final class JJCalls {
    int gen;
    Token first;
    int arg;
    JJCalls next;
  }

}