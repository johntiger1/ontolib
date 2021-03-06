package com.github.phenomics.ontolib.io.obo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

import com.github.phenomics.ontolib.ontology.data.TermSynonymScope;
import com.google.common.collect.Lists;

import de.charite.compbio.ontolib.io.obo.parser.Antlr4OboParser.DbXrefContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OboParser.DbXrefListContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OboParser.EolComment2Context;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OboParser.ExtWordContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OboParser.HeaderContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OboParser.HeaderKeyValueContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OboParser.InstanceStanzaKeyValueContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OboParser.KeyValueAltIdContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OboParser.KeyValueAutoGeneratedByContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OboParser.KeyValueCommentContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OboParser.KeyValueConsiderContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OboParser.KeyValueCreatedByContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OboParser.KeyValueCreationDateContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OboParser.KeyValueDataVersionContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OboParser.KeyValueDateContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OboParser.KeyValueDefContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OboParser.KeyValueDefaultRelationshipIdPrefixContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OboParser.KeyValueDisjointFromContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OboParser.KeyValueDomainContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OboParser.KeyValueFormatVersionContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OboParser.KeyValueGenericContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OboParser.KeyValueIdContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OboParser.KeyValueIdMappingContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OboParser.KeyValueIdspaceContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OboParser.KeyValueImportContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OboParser.KeyValueInstanceOfContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OboParser.KeyValueIntersectionOfContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OboParser.KeyValueInverseOfContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OboParser.KeyValueIsAContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OboParser.KeyValueIsAnonymousContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OboParser.KeyValueIsAntisymmetricContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OboParser.KeyValueIsCyclicContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OboParser.KeyValueIsMetadataContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OboParser.KeyValueIsObsoleteContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OboParser.KeyValueIsReflexiveContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OboParser.KeyValueIsSymmetricContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OboParser.KeyValueIsTransitiveContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OboParser.KeyValueNameContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OboParser.KeyValueRangeContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OboParser.KeyValueRelationshipContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OboParser.KeyValueRemarkContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OboParser.KeyValueReplacedByContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OboParser.KeyValueSavedByContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OboParser.KeyValueSubsetContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OboParser.KeyValueSubsetdefContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OboParser.KeyValueSynonymContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OboParser.KeyValueSynonymtypedefContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OboParser.KeyValueTransitiveOverContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OboParser.KeyValueUnionOfContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OboParser.KeyValueXrefContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OboParser.StanzaContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OboParser.TermStanzaKeyValueContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OboParser.TrailingModifierContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OboParser.TrailingModifierKeyValueContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OboParser.TypedefStanzaKeyValueContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OboParserBaseListener;

// TODO: validate the cardinality of entries in header/stanza
// TODO: using Optional<> for optioanl entries would greatly simplify downstream processing

/**
 * Master <code>ParseTreeListener</code> to use for OBO parsing.
 *
 * <p>
 * This class itself uses the listener pattern for customizing behaviour of storing the header and
 * collecting all stanzas or an event-based implementation using {@link OboParseResultListener}.
 * </p>
 *
 * @author <a href="mailto:manuel.holtgrewe@bihealth.de">Manuel Holtgrewe</a>
 */
class OboParserListener extends Antlr4OboParserBaseListener {

  /** Internal listener for notifying on parsed parts. */
  private final OboParseResultListener listener;

  /** Map nodes to Objects with Map&lt;ParseTree, Object&gt;. */
  private ParseTreeProperty<Object> values = new ParseTreeProperty<>();

  /**
   * Current {@link StanzaType}.
   */
  private StanzaType stanzaType = null;

  /**
   * List of current stanza's {@link StanzaEntry} objects.
   *
   * <p>
   * This has package level visibility for testing purposes.
   * </p>
   */
  List<StanzaEntry> stanzaKeyValues = null;

  /**
   * Constructor.
   *
   * @param listener {@link OboParseResultListener} to use.
   */
  public OboParserListener(OboParseResultListener listener) {
    this.listener = listener;
  }

  /**
   * Set associated <code>value</code> for the given {@link ParseTree} <code>node</code>.
   *
   * @param node The {@link ParseTree} <code>node</code> to set the value for.
   * @param value The value to put for the given {@link ParseTree} <code>node</code>.
   */
  private void setValue(ParseTree node, Object value) {
    values.put(node, value);
  }

  /**
   * Retrieve object stored for <code>node</code>.
   *
   * <p>
   * Note that this just has package level access such that this class can be conveniently tested.
   * </p>
   *
   * @param node The {@link ParseTree} node to get the value for.
   * @return The value associated with <code>node</code>.
   */
  Object getValue(ParseTree node) {
    return values.get(node);
  }

  /**
   * Flush the {@link #values} mapping for allowing garbage collection.
   */
  void clearValues() {
    values = new ParseTreeProperty<>();
  }

  /**
   * Called on entering <code>stanza</code> rule.
   */
  @Override
  public void enterHeader(HeaderContext ctx) {
    // Create new list of key/value pairs
    stanzaKeyValues = new ArrayList<StanzaEntry>();
  }

  /**
   * Called on leaving <code>header</code>.
   */
  @Override
  public void exitHeader(HeaderContext ctx) {
    // Notify listener about being done with the header parsing
    if (listener != null) {
      listener.parsedHeader(Header.create(stanzaKeyValues));
    }

    // Zap stanza type and list of key/value pairs
    stanzaType = null;
    stanzaKeyValues = null;
    // Clear mapping of parse tree nodes
    clearValues();
  }

  /**
   * Called on leaving <code>stanza</code> rule.
   */
  @Override
  public void exitStanza(StanzaContext ctx) {
    // Extract and store current stanza type
    if (ctx.termStanza() != null) {
      stanzaType = StanzaType.TERM;
    } else if (ctx.typedefStanza() != null) {
      stanzaType = StanzaType.TYPEDEF;
    } else if (ctx.instanceStanza() != null) {
      stanzaType = StanzaType.INSTANCE;
    }
    // else: header is a fake stanza, nop

    // Construct Stanza object and notify listener
    if (listener != null) {
      listener.parsedStanza(Stanza.create(stanzaType, stanzaKeyValues));
    }

    // Zap stanza type and list of key/value pairs
    stanzaType = null;
    stanzaKeyValues = null;
    // Clear all values attached to parse tree nodes
    clearValues();
  }

  /**
   * Called on entering <code>stanza</code> rule.
   */
  @Override
  public void enterStanza(StanzaContext ctx) {
    // Create new list of key/value pairs
    stanzaKeyValues = new ArrayList<StanzaEntry>();
  }

  /**
   * Called on leaving <code>termKeyValue</code>, moves child <code>keyValue*</code> to this
   * {@link ParseTree}'s map entry.
   */
  @Override
  public void exitTermStanzaKeyValue(TermStanzaKeyValueContext ctx) {
    setValue(ctx, getValue(ctx.getChild(0)));
    stanzaKeyValues.add((StanzaEntry) getValue(ctx.getChild(0)));
  }

  /**
   * Called on leaving <code>instanceKeyValue</code>, moves child <code>keyValue*</code> to this
   * {@link ParseTree}'s map entry.
   */
  @Override
  public void exitInstanceStanzaKeyValue(InstanceStanzaKeyValueContext ctx) {
    setValue(ctx, getValue(ctx.getChild(0)));
    stanzaKeyValues.add((StanzaEntry) getValue(ctx.getChild(0)));
  }

  /**
   * Called on leaving <code>typedefKeyValue</code>, moves child <code>keyValue*</code> to this
   * {@link ParseTree}'s map entry.
   */
  @Override
  public void exitTypedefStanzaKeyValue(TypedefStanzaKeyValueContext ctx) {
    setValue(ctx, getValue(ctx.getChild(0)));
    stanzaKeyValues.add((StanzaEntry) getValue(ctx.getChild(0)));
  }

  /**
   * Called on leaving <code>headerKeyValue</code>, moves child <code>keyValue*</code> to this
   * {@link ParseTree}'s map entry.
   */
  @Override
  public void exitHeaderKeyValue(HeaderKeyValueContext ctx) {
    setValue(ctx, getValue(ctx.getChild(0)));
    stanzaKeyValues.add((StanzaEntry) getValue(ctx.getChild(0)));
  }

  /** Called on leaving <code>keyValueId</code> rule. */
  @Override
  public void exitKeyValueId(KeyValueIdContext ctx) {
    final String id = OboEscapeUtils.unescape(ctx.stringValue().getText());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryId(id, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueName</code> rule. */
  @Override
  public void exitKeyValueName(KeyValueNameContext ctx) {
    final String name = OboEscapeUtils.unescape(ctx.stringValue().getText());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryName(name, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueIsAnonymous</code> rule. */
  @Override
  public void exitKeyValueIsAnonymous(KeyValueIsAnonymousContext ctx) {
    final boolean isAnonymous = ctx.BooleanValue().getText().equals("true");
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryIsAnonymous(isAnonymous, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueIsAltId</code> rule. */
  @Override
  public void exitKeyValueAltId(KeyValueAltIdContext ctx) {
    final String altId = OboEscapeUtils.unescape(ctx.stringValue().getText());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryAltId(altId, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueDef</code> rule. */
  @Override
  public void exitKeyValueDef(KeyValueDefContext ctx) {
    final String text = OboEscapeUtils.unescape(ctx.QuotedString().getText());
    final DbXrefList dbXrefList = (DbXrefList) getValue(ctx.dbXrefList());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryDef(text, dbXrefList, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueComment</code> rule. */
  @Override
  public void exitKeyValueComment(KeyValueCommentContext ctx) {
    final String text = OboEscapeUtils.unescape(ctx.stringValue().getText());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryComment(text, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueSubset</code> rule. */
  @Override
  public void exitKeyValueSubset(KeyValueSubsetContext ctx) {
    final String name = OboEscapeUtils.unescape(ctx.stringValue().getText());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntrySubset(name, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueSynonym</code> rule. */
  @Override
  public void exitKeyValueSynonym(KeyValueSynonymContext ctx) {
    final String text = OboEscapeUtils.unescape(ctx.QuotedString().getText());
    final TermSynonymScope termSynonymScope =
        TermSynonymScope.valueOf(ctx.ScopeIdentifier().getText());
    final String scopeTypeName = OboEscapeUtils.unescape(trimmedEmptyToNull(ctx.extWord()));
    final DbXrefList dbXrefList = (DbXrefList) getValue(ctx.dbXrefList());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntrySynonym(text, termSynonymScope, scopeTypeName, dbXrefList,
        trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueXref</code> rule. */
  @Override
  public void exitKeyValueXref(KeyValueXrefContext ctx) {
    final DbXref dbXref = (DbXref) getValue(ctx.dbXref());
    final TrailingModifier trailingModifier = dbXref.getTrailingModifier();
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryXref(dbXref, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueIsA</code> rule. */
  @Override
  public void exitKeyValueIsA(KeyValueIsAContext ctx) {
    final String id = OboEscapeUtils.unescape(ctx.extWord().getText());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryIsA(id, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueIntersectionOf</code> rule. */
  @Override
  public void exitKeyValueIntersectionOf(KeyValueIntersectionOfContext ctx) {
    final String relationshipType;
    final String id;
    if (ctx.extWord().size() == 1) {
      relationshipType = null;
      id = OboEscapeUtils.unescape(ctx.extWord(0).getText());
    } else {
      relationshipType = OboEscapeUtils.unescape(ctx.extWord(0).getText());
      id = OboEscapeUtils.unescape(ctx.extWord(1).getText());
    }
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryIntersectionOf(relationshipType, id, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueUnionOf</code> rule. */
  @Override
  public void exitKeyValueUnionOf(KeyValueUnionOfContext ctx) {
    final String id = OboEscapeUtils.unescape(ctx.extWord().getText());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryUnionOf(id, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueDisjointFrom</code> rule. */
  @Override
  public void exitKeyValueDisjointFrom(KeyValueDisjointFromContext ctx) {
    final String id = OboEscapeUtils.unescape(ctx.extWord().getText());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryDisjointFrom(id, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueRelationship</code> rule. */
  @Override
  public void exitKeyValueRelationship(KeyValueRelationshipContext ctx) {
    final String relationshipType;
    final List<String> ids;
    if (ctx.extWord().size() == 2) {
      relationshipType = OboEscapeUtils.unescape(ctx.extWord(0).getText());
      ids = Lists.newArrayList(OboEscapeUtils.unescape(ctx.extWord(1).getText()));
    } else {
      relationshipType = OboEscapeUtils.unescape(ctx.extWord(0).getText());
      ids = ctx.extWord().subList(1, ctx.extWord().size()).stream()
          .map(t -> OboEscapeUtils.unescape(t.getText())).collect(Collectors.toList());
    }
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryRelationship(relationshipType, ids, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueIsObsolete</code> rule. */
  @Override
  public void exitKeyValueIsObsolete(KeyValueIsObsoleteContext ctx) {
    final boolean isObsolete = ctx.BooleanValue().getText().equals("true");
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryIsObsolete(isObsolete, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueReplacedBy</code> rule. */
  @Override
  public void exitKeyValueReplacedBy(KeyValueReplacedByContext ctx) {
    final String id = OboEscapeUtils.unescape(ctx.stringValue().getText());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryReplacedBy(id, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueConsider</code> rule. */
  @Override
  public void exitKeyValueConsider(KeyValueConsiderContext ctx) {
    final String id = OboEscapeUtils.unescape(ctx.stringValue().getText());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryConsider(id, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueCreatedBy</code> rule. */
  @Override
  public void exitKeyValueCreatedBy(KeyValueCreatedByContext ctx) {
    final String creator = OboEscapeUtils.unescape(ctx.stringValue().getText());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryCreatedBy(creator, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueCreationDate</code> rule. */
  @Override
  public void exitKeyValueCreationDate(KeyValueCreationDateContext ctx) {
    final String date = OboEscapeUtils.unescape(ctx.stringValue().getText());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryCreationDate(date, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueGeneric</code> rule. */
  @Override
  public void exitKeyValueGeneric(KeyValueGenericContext ctx) {
    final String tag = OboEscapeUtils.unescape(ctx.GenericStanzaTag().getText());
    final String text = OboEscapeUtils.unescape(ctx.stringValue().getText());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryGeneric(tag, text, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueDomain</code> rule. */
  @Override
  public void exitKeyValueDomain(KeyValueDomainContext ctx) {
    final String value = OboEscapeUtils.unescape(ctx.stringValue().getText());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryDomain(value, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueRange</code> rule. */
  @Override
  public void exitKeyValueRange(KeyValueRangeContext ctx) {
    final String value = OboEscapeUtils.unescape(ctx.stringValue().getText());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryRange(value, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueInverseOf</code> rule. */
  @Override
  public void exitKeyValueInverseOf(KeyValueInverseOfContext ctx) {
    final String id = OboEscapeUtils.unescape(ctx.stringValue().getText());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryInverseOf(id, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueTransitiveOver</code> rule. */
  @Override
  public void exitKeyValueTransitiveOver(KeyValueTransitiveOverContext ctx) {
    final String id = OboEscapeUtils.unescape(ctx.stringValue().getText());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryTransitiveOver(id, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueIsCyclic</code> rule. */
  @Override
  public void exitKeyValueIsCyclic(KeyValueIsCyclicContext ctx) {
    final boolean isCyclic = ctx.BooleanValue().getText().equals("true");
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryIsCyclic(isCyclic, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueIsReflexive</code> rule. */
  @Override
  public void exitKeyValueIsReflexive(KeyValueIsReflexiveContext ctx) {
    final boolean isReflexive = ctx.BooleanValue().getText().equals("true");
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryIsReflexive(isReflexive, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueIsSymmetric</code> rule. */
  @Override
  public void exitKeyValueIsSymmetric(KeyValueIsSymmetricContext ctx) {
    final boolean isSymmetric = ctx.BooleanValue().getText().equals("true");
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryIsSymmetric(isSymmetric, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueIsAntisymmetric</code> rule. */
  @Override
  public void exitKeyValueIsAntisymmetric(KeyValueIsAntisymmetricContext ctx) {
    final boolean isAntisymmetric = ctx.BooleanValue().getText().equals("true");
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryIsAntisymmetric(isAntisymmetric, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueIsTransitive</code> rule. */
  @Override
  public void exitKeyValueIsTransitive(KeyValueIsTransitiveContext ctx) {
    final boolean isTransitive = ctx.BooleanValue().getText().equals("true");
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryIsTransitive(isTransitive, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueIsMetadata</code> rule. */
  @Override
  public void exitKeyValueIsMetadata(KeyValueIsMetadataContext ctx) {
    final boolean isMetadata = ctx.BooleanValue().getText().equals("true");
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryIsMetadata(isMetadata, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueInstanceOf</code> rule. */
  @Override
  public void exitKeyValueInstanceOf(KeyValueInstanceOfContext ctx) {
    final String id = OboEscapeUtils.unescape(ctx.stringValue().getText());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryInstanceOf(id, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueFormatVersion</code> rule. */
  @Override
  public void exitKeyValueFormatVersion(KeyValueFormatVersionContext ctx) {
    final String value = ctx.stringValue().getText();
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryFormatVersion(value, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueDataVersion</code> rule. */
  @Override
  public void exitKeyValueDataVersion(KeyValueDataVersionContext ctx) {
    final String value = OboEscapeUtils.unescape(ctx.stringValue().getText());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryDataVersion(value, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueDate</code> rule. */
  @Override
  public void exitKeyValueDate(KeyValueDateContext ctx) {
    final String value = OboEscapeUtils.unescape(ctx.stringValue().getText());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryDate(value, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueSavedBy</code> rule. */
  @Override
  public void exitKeyValueSavedBy(KeyValueSavedByContext ctx) {
    final String value = OboEscapeUtils.unescape(ctx.stringValue().getText());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntrySavedBy(value, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueAutogeneratedBy</code> rule. */
  @Override
  public void exitKeyValueAutoGeneratedBy(KeyValueAutoGeneratedByContext ctx) {
    final String creator = OboEscapeUtils.unescape(ctx.stringValue().getText());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryAutoGeneratedBy(creator, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueSubsetdef</code> rule. */
  @Override
  public void exitKeyValueSubsetdef(KeyValueSubsetdefContext ctx) {
    final String id = OboEscapeUtils.unescape(ctx.extWord().getText());
    final String description = OboEscapeUtils.unescape(ctx.QuotedString().getText());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntrySubsetdef(id, description, trailingModifier, comment));
  }

  /** Called on entering <code>keyValueImport</code> rule. */
  @Override
  public void exitKeyValueImport(KeyValueImportContext ctx) {
    final String value = OboEscapeUtils.unescape(ctx.stringValue().getText());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryImport(value, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueSynonymTypedef</code> rule. */
  @Override
  public void exitKeyValueSynonymtypedef(KeyValueSynonymtypedefContext ctx) {
    final String synonymTypeName = OboEscapeUtils.unescape(ctx.extWord().getText());
    final String description = OboEscapeUtils.unescape(ctx.QuotedString().getText());
    final TermSynonymScope termSynonymScope;
    if (ctx.ScopeIdentifier() != null) {
      termSynonymScope = TermSynonymScope.valueOf(ctx.ScopeIdentifier().getText());
    } else {
      termSynonymScope = null;
    }
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntrySynonymtypedef(synonymTypeName, description, termSynonymScope,
        trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueDefaultRelationshipIdspace</code> rule. */
  @Override
  public void exitKeyValueIdspace(KeyValueIdspaceContext ctx) {
    final String localIdSpace = OboEscapeUtils.unescape(ctx.extWord(0).getText());
    final String remoteIdSpace = OboEscapeUtils.unescape(ctx.extWord(1).getText());
    final String description;
    if (ctx.QuotedString() != null) {
      description = OboEscapeUtils.unescape(ctx.QuotedString().getText());
    } else {
      description = null;
    }
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryIdspace(localIdSpace, remoteIdSpace, description, trailingModifier,
        comment));
  }

  /** Called on leaving <code>keyValueDefaultRelationshipPrefix</code> rule. */
  @Override
  public void exitKeyValueDefaultRelationshipIdPrefix(
      KeyValueDefaultRelationshipIdPrefixContext ctx) {
    final String id = OboEscapeUtils.unescape(ctx.extWord().getText());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryDefaultRelationshipIdPrefix(id, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueIdMapping</code> rule. */
  @Override
  public void exitKeyValueIdMapping(KeyValueIdMappingContext ctx) {
    final String sourceId = OboEscapeUtils.unescape(ctx.extWord(0).getText());
    final String targetId = OboEscapeUtils.unescape(ctx.extWord(1).getText());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryIdMapping(sourceId, targetId, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueRemark</code> rule. */
  @Override
  public void exitKeyValueRemark(KeyValueRemarkContext ctx) {
    final String text = OboEscapeUtils.unescape(ctx.stringValue().getText());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryRemark(text, trailingModifier, comment));
  }

  /**
   * Called on leaving <code>dbXrefList</code> rule, construct {@link DbXref} object.
   */
  @Override
  public void exitDbXrefList(DbXrefListContext ctx) {
    final DbXrefList dbXrefList = new DbXrefList();
    for (DbXrefContext xrcCtx : ctx.dbXref()) {
      dbXrefList.addDbXref((DbXref) getValue(xrcCtx));
    }

    setValue(ctx, dbXrefList);
  }

  /**
   * Called on leaving <code>dbXref</code> rule, construct {@link DbXref} object.
   */
  @Override
  public void exitDbXref(DbXrefContext ctx) {
    final String name = OboEscapeUtils.unescape(ctx.dbXrefWord().getText());
    final String description = (ctx.QuotedString() == null)
        ? null
        : OboEscapeUtils.unescape(ctx.QuotedString().getText().trim());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());

    setValue(ctx, new DbXref(name, description, trailingModifier));
  }

  /**
   * Called on leaving <code>trailingModifier</code> rule, construct {@link TrailingModifier}
   * object.
   */
  @Override
  public void exitTrailingModifier(TrailingModifierContext ctx) {
    final TrailingModifierBuilder builder = new TrailingModifierBuilder();
    for (TrailingModifierKeyValueContext kvCtx : ctx.trailingModifierKeyValue()) {
      if (kvCtx.QuotedString() != null) {
        builder.addKeyValue(OboEscapeUtils.unescape(kvCtx.Word(0).getText().trim()),
            OboEscapeUtils.unescape(kvCtx.QuotedString().getText().trim()));
      } else {
        builder.addKeyValue(OboEscapeUtils.unescape(kvCtx.Word(0).getText().trim()),
            OboEscapeUtils.unescape(kvCtx.Word(1).getText().trim()));
      }
    }

    setValue(ctx, builder.build());
  }

  /**
   * Convert <code>null</code> and (trimmed) empty string to <code>null</code>.
   *
   * @param s <code>String</code> to convert
   * @return Trimmed value of <code>s</code> or <code>null</code>.
   */
  private static String trimmedEmptyToNull(String s) {
    if (s == null) {
      return null;
    } else {
      final String str = s.trim();
      if (str.isEmpty()) {
        return null;
      } else {
        return str.trim();
      }
    }
  }

  /**
   * Overload of {@link #trimmedEmptyToNull(String)} that handles {@link EolComment2Context}.
   */
  private static String trimmedEmptyToNull(EolComment2Context x) {
    if (x == null || x.Comment2() == null) {
      return null;
    } else {
      final String text = x.Comment2().getText();
      final int pos = text.indexOf('!');
      return trimmedEmptyToNull(text.substring(pos + 1, text.length()).trim());
    }
  }

  /**
   * Overload of {@link #trimmedEmptyToNull(String)} that handles {@link ExtWordContext}.
   */
  private static String trimmedEmptyToNull(ExtWordContext n) {
    if (n == null) {
      return null;
    } else {
      return trimmedEmptyToNull(n.getText());
    }
  }

}
